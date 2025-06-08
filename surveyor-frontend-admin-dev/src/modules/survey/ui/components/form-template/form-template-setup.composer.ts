import { useAppStore } from "@/app/store/app.store";
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { StatusType } from "@/modules/common/model/http.response-message.model";
import EnvironmentProvider from "@/modules/common/service/env.provider";
import EzAlert, { DialogResult } from "@/modules/common/ui/composables/ez-alert";
import { PrincipalType } from "@/modules/core/constant/principal-type.const";
import { UserGroup } from "@/modules/core/model/user-group.model";
import { FormTemplateGroup } from "@/modules/survey/model/form-template-group.model";
import { FormTemplateInfo } from "@/modules/survey/model/form-template-info.model";
import { FormTemplate, TemplateStatus } from "@/modules/survey/model/form-template.model";
import formTemplateService, { FormTemplateCreateRequestMessage, FormTemplateCreateResponseMessage, FormTemplateSearchRequestMessage, FormTemplateSearchResponseMessage, FormTemplateUpdateRequestMessage, FormTemplateUpdateResponseMessage } from "@/modules/survey/service/form-template.service";
import { defineStore } from "pinia";
import { reactive } from "vue";

const formTemplateSetupStore = defineStore("survey.form-template.setup", () => {
    console.info("### FormTemplateSetupStore ###");

    const state = reactive({
        mode: "list" as "list" | "entry",
        entryMode: "" as "add" | "edit" | "",
        
        searchCriteria: {
            templateGroupId: "",
            templateName: "",
            templateStatus: ""
        },

        templateForm: {
            templateId: "",
            templateGroupId: "",
            templateCode: "",
            templateName: "",
            templateDescription: "",
            remark: "",
            notify: true,
            templateStatus: "",
            status: StatusCodeConst.Active,
            userGroupIds: [] as string[],

            authorities: [] as {
                principalType: PrincipalType,
                principalRefId: string
            } []
        },

        templateStatuses: Object.values(TemplateStatus),
        statusCodes: Object.values(StatusCodeConst),
        formTemplateGroups: [] as FormTemplateGroup[],
        userGroups: [] as UserGroup[],
        formTemplateInfos: [] as FormTemplateInfo[]
    });

    function init()
    {
        console.log("### FormTemplateSetupStore.init ###");
        sessionStorage.setItem("server_url", EnvironmentProvider.getServerEndpoint());
        sessionStorage.removeItem("form_template_id");
        sessionStorage.removeItem("form_template_element_data");
        resetState();
    }

    function destroy()
    {
        console.log("### FormTemplateSetupStore.destroy ###");
        sessionStorage.removeItem("server_url");
        sessionStorage.removeItem("form_template_id");
        sessionStorage.removeItem("form_template_element_data");
        resetState();
    }

    function resetState() 
    {
        console.log("### FormTemplateSetupStore.resetState ###");

        state.mode = "list";
        state.entryMode = "";

        state.searchCriteria = {
            templateGroupId: "",
            templateName: "",
            templateStatus: ""
        };

        state.templateForm = {
            templateId: "",
            templateGroupId: "",
            templateCode: "",
            templateName: "",
            templateDescription: "",
            remark: "",
            notify: true,
            templateStatus: "",
            status: StatusCodeConst.Active,
            userGroupIds: [],

            authorities: []
        };

        state.templateStatuses = Object.values(TemplateStatus);
        state.statusCodes = Object.values(StatusCodeConst);
        state.formTemplateGroups = [];
        state.userGroups = [];
        state.formTemplateInfos = [];
    }
  
    return {
        state,

        init,
        destroy
    };
});


export const useFormTemplateSetupComposer = () => {
    const store = formTemplateSetupStore();
    const state = store.state;
    const appStore = useAppStore();

    function init()
    {
        console.log("### FormTemplateSetupComposor.init ###");
        store.init();
        loadScreen();
    }

    function destroy()
    {
        console.log("### FormTemplateSetupComposor.destroy ###");
        store.destroy();
    }

    function toListMode() {
        state.mode = 'list';
        state.entryMode = '';
    }

    function toAddEntryMode() {
        state.mode = 'entry';
        state.entryMode = 'add';
    }

    function toEditEntryMode() {
        state.mode = 'entry';
        state.entryMode = 'edit';
    }

    function getFormTemplateGroupName(formTemplate: FormTemplate): string {
        const formTemplateGroup = state.formTemplateGroups.find(group => group.id === formTemplate.templateGroupId);
        return formTemplateGroup?.name || "";
    }

    const resetSearchCriteria = () => {
        console.log("### FormTemplateSetupComposor.resetSearchCriteria ###");

        state.searchCriteria = {
            templateName: "",
            templateGroupId: "",
            templateStatus: ""
        };
    };

    const loadScreen = async () => {
        console.log("### FormTemplateSetupComposor.loadScreen ###");

        appStore.startLoading();
        try {
            const responseMessage = await formTemplateService.loadScreen({});

            if(responseMessage.head?.status?.type === StatusType.Success) {
                state.formTemplateGroups = responseMessage.body?.formTemplateGroups || [];
                state.userGroups = responseMessage.body?.userGroups || [];
            } else {
                EzAlert.showError({
                    title: responseMessage.head?.status?.title,
                    text: responseMessage.head?.status?.message,
                    buttonText: "OK",
                });
            }
        } catch (error: any) {
            EzAlert.showError({
                title: error.type,
                text: error.message,
                buttonText: "OK",
            });
        } finally {
            appStore.stopLoading();
        }
    };

    const search = async () => {
        console.log("### FormTemplateSetupComposor.search ###");

        state.formTemplateInfos = [];

        const requestMessage: FormTemplateSearchRequestMessage = {
            requestPayload: {
                criteria: {
                    templateGroupId: state.searchCriteria.templateGroupId,
                    templateName: state.searchCriteria.templateName,
                    templateStatus: state.searchCriteria.templateStatus,
                    status: StatusCodeConst.Active
                }
            }
        };
        
        appStore.startLoading();
        try {
            const responseMessage: FormTemplateSearchResponseMessage = await formTemplateService.search(requestMessage);
            
            if(responseMessage.head?.status?.type === StatusType.Success) {
                state.formTemplateInfos = responseMessage.body?.responsePayload?.formTemplateInfos || [];                    
            } else {
                EzAlert.showError({
                    title: responseMessage.head?.status?.title,
                    text: responseMessage.head?.status?.message,
                    buttonText: "OK",
                });
            }
        } catch (error: any) {
            EzAlert.showError({
                title: error.type,
                text: error.message,
                buttonText: "OK",
            });
        } finally {
            appStore.stopLoading();
        }
    };

    const save = () => {
        console.log("### FormTemplateSetupComposor.save ###");

        if(! validateForm()) {
            return;
        }

        EzAlert.showConfirm({
            title: "Confirm",
            text: "Do you want to save?",
            buttonText: "Yes",
            cancelButtonText: "No",
            buttonClickedHandler: (result: DialogResult) => {
                if(result.isConfirmed) {
                    if(state.entryMode === "add") {
                        create();
                    } else if(state.entryMode === "edit") {
                        update();
                    }
                }
            }
        });
    };

    const create = async () => {
        console.log("### FormTemplateSetupComposor.create ###");

        appStore.startLoading();
        try {
            const requestMessage: FormTemplateCreateRequestMessage = {
                requestPayload: {
                    templateGroupId: state.templateForm.templateGroupId,
                    templateCode: state.templateForm.templateCode,
                    templateName: state.templateForm.templateName,
                    templateDescription: state.templateForm.templateDescription,
                    remark: state.templateForm.remark,
                    templateStatus: state.templateForm.templateStatus,
                    status: state.templateForm.status,

                    authorities: state.templateForm.userGroupIds.map(userGroupId => {
                        return {
                            principalType: PrincipalType.UserGroup,
                            principalRefId: userGroupId
                        }
                    })
                }
            };

            const responseMessage: FormTemplateCreateResponseMessage = await formTemplateService.create(requestMessage);

            if(responseMessage.head?.status?.type === StatusType.Success) {
                EzAlert.showSuccess({
                    title: responseMessage.head?.status?.title,
                    text: responseMessage.head?.status?.message,
                    buttonText: "OK",
                });

                // toListMode();
                search();

                if(responseMessage.body?.responsePayload.formTemplateInfo.formTemplate?.id) {
                    sessionStorage.setItem("form_template_id", responseMessage.body?.responsePayload.formTemplateInfo.formTemplate?.id);
                }
            } else {
                EzAlert.showError({
                    title: responseMessage.head?.status?.title,
                    text: responseMessage.head?.status?.message,
                    buttonText: "OK",
                });
            }
        } catch (error: any) {
            EzAlert.showError({
                title: error.type,
                text: error.message,
                buttonText: "OK",
            });
        } finally {
            appStore.stopLoading();
        }
    };

    const update = async () => {
        console.log("### FormTemplateSetupComposor.update ###");

        appStore.startLoading();
        try {
            const requestMessage: FormTemplateUpdateRequestMessage = {
                requestPayload: {
                    parentTemplateId: state.templateForm.templateId,
                    templateGroupId: state.templateForm.templateGroupId,
                    templateCode: state.templateForm.templateCode,
                    templateName: state.templateForm.templateName,
                    templateDescription: state.templateForm.templateDescription,
                    remark: state.templateForm.remark,
                    templateStatus: state.templateForm.templateStatus,
                    status: state.templateForm.status,

                    authorities: state.templateForm.userGroupIds.map(userGroupId => {
                        return {
                            principalType: PrincipalType.UserGroup,
                            principalRefId: userGroupId
                        }
                    })
                }
            };

            const responseMessage: FormTemplateUpdateResponseMessage = await formTemplateService.update(requestMessage);
            if(responseMessage.head?.status?.type === StatusType.Success) {
                EzAlert.showSuccess({
                    title: responseMessage.head?.status?.title,
                    text: responseMessage.head?.status?.message,
                    buttonText: "OK",
                });

                toListMode();
                search();
            } else {
                EzAlert.showError({
                    title: responseMessage.head?.status?.title,
                    text: responseMessage.head?.status?.message,
                    buttonText: "OK",
                });
            }
        } catch (error: any) {
            EzAlert.showError({
                title: error.type,
                text: error.message,
                buttonText: "OK",
            });
        } finally {
            appStore.stopLoading();
        }
    };

    const validateForm = (): boolean => {
        console.log("### FormTemplateSetupComposor.validateForm ###");

        if(state.templateForm.templateGroupId === "") {
            EzAlert.showError({
                title: "Error",
                text: "Please select Form Template Group",
                buttonText: "OK",
            });
            return false;
        }

        if(state.templateForm.templateCode === "") {
            EzAlert.showError({
                title: "Error",
                text: "Please enter Form Template Code",
                buttonText: "OK",
            });
            return false;
        }

        if(state.templateForm.templateName === "") {
            EzAlert.showError({
                title: "Error",
                text: "Please enter Form Template Name",
                buttonText: "OK",
            });
            return false;
        }

        if(state.templateForm.templateDescription === "") {
            EzAlert.showError({
                title: "Error",
                text: "Please enter Form Template Description",
                buttonText: "OK",
            });
            return false;
        }

        if(state.templateForm.templateStatus === "") {
            EzAlert.showError({
                title: "Error",
                text: "Please select Form Template Status",
                buttonText: "OK",
            });
            return false;
        }

        if(state.templateForm.userGroupIds.length === 0) {
            EzAlert.showError({
                title: "Error",
                text: "Please select User Group Authorities",
                buttonText: "OK",
            });
            return false;
        }

        return true;
    };

    const resetForm = () => {
        console.log("### FormTemplateSetupComposor.resetForm ###");

        state.templateForm = {
            templateId: "",
            templateGroupId: "",
            templateCode: "",
            templateName: "",
            templateDescription: "",
            remark: "",
            notify: true,
            templateStatus: "",
            status: StatusCodeConst.Active,
            userGroupIds: [],

            authorities: []
        }
    };

    return {
        state,

        init,
        destroy,
        loadScreen,
        resetSearchCriteria,
        toListMode,
        toAddEntryMode,
        toEditEntryMode,
        getFormTemplateGroupName,
        search,
        save,
        resetForm
    };

};