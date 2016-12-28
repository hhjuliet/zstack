package org.zstack.header.network.l2

import java.lang.Integer
import java.sql.Timestamp
import java.sql.Timestamp

doc {

	title "在这里输入结构的名称"

	field {
		name "vlan"
		desc ""
		type "Integer"
		since "0.6"
	}
	field {
		name "uuid"
		desc "资源的UUID，唯一标示该资源"
		type "String"
		since "0.6"
	}
	field {
		name "name"
		desc "资源名称"
		type "String"
		since "0.6"
	}
	field {
		name "description"
		desc "资源的详细描述"
		type "String"
		since "0.6"
	}
	field {
		name "zoneUuid"
		desc "区域UUID"
		type "String"
		since "0.6"
	}
	field {
		name "physicalInterface"
		desc ""
		type "String"
		since "0.6"
	}
	field {
		name "type"
		desc ""
		type "String"
		since "0.6"
	}
	field {
		name "createDate"
		desc "创建时间"
		type "Timestamp"
		since "0.6"
	}
	field {
		name "lastOpDate"
		desc "最后一次修改时间"
		type "Timestamp"
		since "0.6"
	}
	field {
		name "attachedClusterUuids"
		desc ""
		type "List"
		since "0.6"
	}
}
