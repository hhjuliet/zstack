package org.zstack.test.integration.storage

import junit.framework.Assert
import org.springframework.http.HttpEntity
import org.zstack.header.vm.VmInstanceState
import org.zstack.header.vm.VmInstanceVO
import org.zstack.header.volume.Volume
import org.zstack.header.volume.VolumeInventory
import org.zstack.kvm.KVMAgentCommands
import org.zstack.kvm.KVMConstant
import org.zstack.sdk.LocalStorageResourceRefInventory
import org.zstack.sdk.VmInstanceInventory
import org.zstack.storage.primary.local.LocalStorageKvmBackend
import org.zstack.storage.primary.local.LocalStorageKvmMigrateVmFlow
import org.zstack.storage.primary.local.LocalStorageSimulatorConfig
import org.zstack.testlib.EnvSpec
import org.zstack.testlib.KVMHostSpec
import org.zstack.testlib.SpringSpec
import org.zstack.testlib.Test
import org.zstack.testlib.VmSpec
import org.zstack.utils.data.SizeUnit
import org.zstack.utils.gson.JSONObjectUtil

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by hhjuliet on 2017/3/3.
 */
class MigrateStorageTest54 extends Test{

	EnvSpec migrateStorageenv

	LocalStorageSimulatorConfig config;

	long totalSize = SizeUnit.GIGABYTE.toByte(100);
	CountDownLatch latch = new CountDownLatch(2);

	static SpringSpec springSpec = makeSpring {
		localStorage()
		nfsPrimaryStorage()
		sftpBackupStorage()
		smp()
		ceph()
		virtualRouter()
		vyos()

		include("TestLocalStorage54.xml")
		include("KVMRelated.xml")
		include("localStorageSimulator.xml")
		include("localStorage.xml")
	}


	@Override
	void setup() {
		useSpring(springSpec)
	}

	@Override
	void environment() {
		migrateStorageenv = MigrateStorageEnv.migrateStorageBasicEnv();
	}

	@Override
	void test() {
		migrateStorageenv.create {

		}
	}

	void testmigrate(){
		LocalStorageSimulatorConfig.Capacity c = new LocalStorageSimulatorConfig.Capacity();
		c.total = totalSize;
		c.avail = totalSize;
		config.capacityMap.put("host1", c);
		config.capacityMap.put("host2", c);

		//stop vm
		VmSpec spec = migrateStorageenv.specByName("vm")
		KVMHostSpec kvmHost1 = migrateStorageenv.specByName("kvm1")
		KVMHostSpec kvmHost2 = migrateStorageenv.specByName("kvm1")

		KVMAgentCommands.StopVmCmd cmd = null

		migrateStorageenv.afterSimulator(KVMConstant.KVM_STOP_VM_PATH) { rsp, HttpEntity<String> e ->
			cmd = JSONObjectUtil.toObject(e.body, KVMAgentCommands.StopVmCmd.class)
			return rsp
		}


		VmInstanceInventory inv = stopVmInstance {
			uuid = spec.inventory.uuid
		}

		assert inv.state == VmInstanceState.Stopped.toString()

		assert cmd != null
		assert cmd.uuid == spec.inventory.uuid

		def vmvo = dbFindByUuid(cmd.uuid, VmInstanceVO.class)
		assert vmvo.state == VmInstanceState.Stopped

		LocalStorageResourceRefInventory localStorageResourceRefInventory = localStorageMigrateVolume {
			volumeUuid = spec.inventory.rootVolumeUuid;
			destHostUuid = kvmHost1.inventory.uuid;
		}

		LocalStorageResourceRefInventory localStorageResourceRefInventory1 = localStorageMigrateVolume {
			volumeUuid = spec.inventory.rootVolumeUuid;
			destHostUuid = kvmHost2.inventory.uuid;
		}
		println("inventory is : "+localStorageResourceRefInventory.resourceUuid)

		latch.await(1, TimeUnit.MINUTES);
		assert 1.equals(config.deleteBitsCmds.size())

		LocalStorageKvmBackend.DeleteBitsCmd cmd1 = config.deleteBitsCmds.get(0)
		assert kvmHost1.inventory.uuid.equals(cmd1.getHostUuid())
		assert 1.equals(config.copyBitsFromRemoteCmds.size())
		LocalStorageKvmMigrateVmFlow.CopyBitsFromRemoteCmd ccmd = config.copyBitsFromRemoteCmds.get(0);
		assert kvmHost2.inventory.managementIp.equals(ccmd.dstIp)
	}



}
