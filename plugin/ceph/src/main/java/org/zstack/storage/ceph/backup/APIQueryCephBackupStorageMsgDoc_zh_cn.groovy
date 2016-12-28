package org.zstack.storage.ceph.backup

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "GET /v1/backup-storage/ceph"

            header (OAuth: 'the-session-uuid')

            clz APIQueryCephBackupStorageMsg.class

            desc ""
            
		params APIQueryMessage.class
        }

        response {
            clz APIQueryBackupStorageReply.class
        }
    }
}