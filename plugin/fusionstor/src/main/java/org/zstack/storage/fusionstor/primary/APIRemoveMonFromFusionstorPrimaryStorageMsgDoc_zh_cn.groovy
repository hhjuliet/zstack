package org.zstack.storage.fusionstor.primary

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "DELETE /v1/primary-storage/fusionstor/{uuid}/mons"

            header (OAuth: 'the-session-uuid')

            clz APIRemoveMonFromFusionstorPrimaryStorageMsg.class

            desc ""
            
			params {

				column {
					name "uuid"
					enclosedIn "params"
					desc "资源的UUID，唯一标示该资源"
					inUrl true
					type "String"
					optional false
					since "0.6"
					
				}
				column {
					name "monHostnames"
					enclosedIn "params"
					desc ""
					inUrl false
					type "List"
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
            clz APIRemoveMonFromFusionstorPrimaryStorageEvent.class
        }
    }
}