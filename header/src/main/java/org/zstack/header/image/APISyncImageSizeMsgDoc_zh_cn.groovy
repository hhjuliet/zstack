package org.zstack.header.image

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "PUT /v1/images/{uuid}/actions"

            header (OAuth: 'the-session-uuid')

            clz APISyncImageSizeMsg.class

            desc ""
            
			params {

				column {
					name "uuid"
					enclosedIn "syncImageSize"
					desc "资源的UUID，唯一标示该资源"
					inUrl true
					type "String"
					optional false
					since "0.6"
					
				}
				column {
					name "systemTags"
					enclosedIn ""
					desc ""
					inUrl false
					type "List"
					optional true
					since "0.6"
					
				}
				column {
					name "userTags"
					enclosedIn ""
					desc ""
					inUrl false
					type "List"
					optional true
					since "0.6"
					
				}
			}
        }

        response {
            clz APISyncImageSizeEvent.class
        }
    }
}