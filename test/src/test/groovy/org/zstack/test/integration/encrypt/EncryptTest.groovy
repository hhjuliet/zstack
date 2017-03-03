package org.zstack.test.integration.encrypt

import org.zstack.header.network.service.NetworkServiceType
import org.zstack.kvm.KVMHostInventory
import org.zstack.kvm.KVMHostVO
import org.zstack.network.securitygroup.SecurityGroupConstant
import org.zstack.network.service.virtualrouter.VirtualRouterConstant
import org.zstack.testlib.EnvSpec
import org.zstack.testlib.KVMHostSpec
import org.zstack.testlib.Test
import org.zstack.utils.data.SizeUnit

/**
 * Created by hhjuliet on 2017/3/1.
 */
class EncryptTest extends Test{
	def Doc ="use test encrypt"
	EnvSpec myenv

	@Override
	void setup() {
		spring{
			sftpBackupStorage()
			localStorage()
			virtualRouter()
			securityGroup()
			kvm()
			include("Kvm.xml")
			include("KVMSimulator.xml")
		}
	}

	@Override
	void environment() {
		myenv = env{
			instanceOffering {
				name = "instanceOffering"
				memory = SizeUnit.GIGABYTE.toByte(8)
				cpu = 4
			}

			sftpBackupStorage {
				name = "sftp"
				url = "/sftp"
				username = "root"
				password = "password"
				hostname = "localhost"

				image {
					name = "image1"
					url = "http://zstack.org/download/test.qcow2"
				}

				image {
					name = "vr"
					url = "http://zstack.org/download/vr.qcow2"
				}
			}


			zone {
				name = "zone"
				description = "test"

				cluster {
					name = "cluster"
					hypervisorType = "KVM"

					attachPrimaryStorage("local")
					attachL2Network("l2")
				}

				localPrimaryStorage {
					name = "local"
					url = "/local_ps"
				}

				l2NoVlanNetwork {
					name = "l2"
					physicalInterface = "eth0"

					l3Network {
						name = "l3"

						service {
							provider = VirtualRouterConstant.PROVIDER_TYPE
							types = [NetworkServiceType.DHCP.toString(), NetworkServiceType.DNS.toString()]
						}

						service {
							provider = SecurityGroupConstant.SECURITY_GROUP_PROVIDER_TYPE
							types = [SecurityGroupConstant.SECURITY_GROUP_NETWORK_SERVICE_TYPE]
						}

						ip {
							startIp = "192.168.100.10"
							endIp = "192.168.100.100"
							netmask = "255.255.255.0"
							gateway = "192.168.100.1"
						}
					}

					l3Network {
						name = "pubL3"

						ip {
							startIp = "12.16.10.10"
							endIp = "12.16.10.100"
							netmask = "255.255.255.0"
							gateway = "12.16.10.1"
						}
					}
				}

				virtualRouterOffering {
					name = "vr"
					memory = SizeUnit.MEGABYTE.toByte(512)
					cpu = 2
					useManagementL3Network("pubL3")
					usePublicL3Network("pubL3")
					useImage("vr")
				}

				attachBackupStorage("sftp")
			}

			vm {
				name = "vm"
				useInstanceOffering("instanceOffering")
				useImage("image1")
				useL3Networks("l3")
			}
		}
	}

	@Override
	void test() {
		myenv.create()
	}

	void testEncrypt(){
		KVMHostSpec kvmspec = myenv.getSpecsByName("kvm")

		println("start123")

		KVMHostInventory kvmHost = addKVMHost {
			name = "kvm1"
			password = "password123456789"
			username = "admin"
			managementIp = "localhost"
		}

		println("finish123")

		KVMHostVO kvmHostVO = dbFindByUuid(kvmHost.getUuid(),KVMHostVO.class)

		System.out.print("kvmHostVo password is : "+kvmHostVO.password);
		System.out.print("host password is : "+kvmHostVO.password);
		System.out.print("host getpassword is : "+kvmHostVO.password);

	}





}
