package org.zstack.header.vm

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "GET /v1/vm-instances/nics"

            header (OAuth: 'the-session-uuid')

            clz APIQueryVmNicMsg.class

            desc ""
            
		params APIQueryMessage.class
        }

        response {
            clz APIQueryVmNicReply.class
        }
    }
}