package org.zstack.header.tag

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "GET /v1/system-tags"

            header (OAuth: 'the-session-uuid')

            clz APIQuerySystemTagMsg.class

            desc ""
            
		params APIQueryMessage.class
        }

        response {
            clz APIQuerySystemTagReply.class
        }
    }
}