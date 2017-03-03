package org.zstack.test.integration.storage

import org.zstack.header.network.service.NetworkServiceType
import org.zstack.network.securitygroup.SecurityGroupConstant
import org.zstack.network.service.virtualrouter.VirtualRouterConstant
import org.zstack.testlib.EnvSpec
import org.zstack.testlib.Test
import org.zstack.utils.data.SizeUnit

/**
 * Created by hhjuliet on 2017/3/3.
 */
class MigrateStorageEnv {
	def DOC = """
use:
1. sftp backup storage
2. local primary storage
3. virtual router provider
4. l2 novlan network
5. security group
"""

	static EnvSpec migrateStorageBasicEnv() {
		return Test.makeEnv {
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
					url  = "http://zstack.org/download/test.qcow2"
				}

				image {
					name = "vr"
					url  = "http://zstack.org/download/vr.qcow2"
				}
			}

			zone {
				name = "zone"
				description = "test"

				cluster {
					name = "cluster"
					hypervisorType = "KVM"

					kvm {
						name = "kvm1"
						managementIp = "localhost"
						username = "root"
						password = "password"

					}

					kvm {
						name = "kvm2"
						managementIp = "127.0.0.1"
						username = "root"
						password = "password"
					}

					attachPrimaryStorage("local")
					attachL2Network("l2")
				}

				localPrimaryStorage {
					name = "local1"
					url = "/test1"

				}
				localPrimaryStorage {
					name = "local2"
					url = "/test2"
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
						name = "pubL3network1"

						ip {
							startIp = "12.16.10.10"
							endIp = "12.16.10.100"
							netmask = "255.255.255.0"
							gateway = "12.16.10.1"
						}
					}

					l3Network {
						name = "pubL3network2"

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
}
