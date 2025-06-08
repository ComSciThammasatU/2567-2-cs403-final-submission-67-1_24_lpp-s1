import { useAppStore } from "@/app/store/app.store";
import { StatusType } from "@/modules/common/model/http.response-message.model";
import EnvironmentProvider from "@/modules/common/service/env.provider";
import EzAlert from "@/modules/common/ui/composables/ez-alert";
import { FormDocumentInfo } from "@/modules/survey/model/form-document-info.model";
import { DocumentStatus } from "@/modules/survey/model/form-document.model";
import { FormTemplateGroup } from "@/modules/survey/model/form-template-group.model";
import { FormTemplate } from "@/modules/survey/model/form-template.model";
import formDocumentService, { FormDocumentSearchRequestMessage, FormDocumentSearchResponseMessage } from "@/modules/survey/service/form_document.service";
import { defineStore } from "pinia";
import { reactive } from "vue";

const formDocumentStore = defineStore("survey.form-document", () => {
    console.info("### FormDocumentStore ###");

    const state = reactive({
        mode: "list" as "list" | "info",
        
        searchCriteria: {
            templateId: "" as string,
            documentStatus: DocumentStatus.Completed as DocumentStatus
        },

        documentStatuses: Object.values(DocumentStatus),
        formTemplateGroups: [] as FormTemplateGroup[],
        formTemplates: [] as FormTemplate[],
        formDocumentInfos: [] as FormDocumentInfo[],
        selectedFormDocumentInfo: undefined as FormDocumentInfo | undefined
    });

    function init()
    {
        console.log("### FormDocumentStore.init ###");
        sessionStorage.setItem("server_url", EnvironmentProvider.getServerEndpoint());
        sessionStorage.removeItem("form_template_id");
        sessionStorage.removeItem("form_document_element_data");
        resetState();
    }

    function destroy()
    {
        console.log("### FormDocumentStore.destroy ###");
        sessionStorage.removeItem("server_url");
        sessionStorage.removeItem("form_template_id");
        sessionStorage.removeItem("form_document_element_data");
        resetState();
    }

    function resetState() 
    {
        console.log("### FormDocumentStore.resetState ###");

        state.mode = "list";

        state.searchCriteria = {
            templateId: "",
            documentStatus: DocumentStatus.Completed
        };

        state.documentStatuses = Object.values(DocumentStatus);
        state.formDocumentInfos = [];
        state.formTemplateGroups = [];
        state.formTemplates = [];
        state.selectedFormDocumentInfo = undefined;
    }
  
    return {
        state,

        init,
        destroy
    };
});


export const useFormDocumentComposer = () => {
    const store = formDocumentStore();
    const state = store.state;
    const appStore = useAppStore();

    function init()
    {
        console.log("### FormDocumentComposor.init ###");
        store.init();
        loadScreen();
    }

    function destroy()
    {
        console.log("### FormDocumentComposor.destroy ###");
        store.destroy();
    }

    function toListMode() {
        state.mode = 'list';
    }

    function toInfoMode() {
        state.mode = 'info';
    }

    const resetSearchCriteria = () => {
        console.log("### FormDocumentComposor.resetSearchCriteria ###");

        state.searchCriteria = {
            templateId: "",
            documentStatus: DocumentStatus.Completed
        };
    };

    const loadScreen = async () => {
        console.log("### FormDocumentComposor.loadScreen ###");

        appStore.startLoading();
        try {
            const responseMessage = await formDocumentService.loadScreen({});

            if(responseMessage.head?.status?.type === StatusType.Success) {
                state.formTemplateGroups = responseMessage.body?.formTemplateGroups || [];
                state.formTemplates = responseMessage.body?.formTemplates || [];
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

        if(state.searchCriteria.templateId === "") {
            EzAlert.showError({
                title: "ระบุเงื่อนไขการค้นหา",
                text: "กรุณาเลือก Template ที่ต้องการค้นหา",
                buttonText: "OK",
            });

            return;
        }

        state.formDocumentInfos = [];

        const requestMessage: FormDocumentSearchRequestMessage = {
            templateId: state.searchCriteria.templateId,
            documentStatus: state.searchCriteria.documentStatus
        };
        
        appStore.startLoading();
        try {
            const responseMessage: FormDocumentSearchResponseMessage = await formDocumentService.searchDocument(requestMessage);
            
            if(responseMessage.head?.status?.type === StatusType.Success) {
                state.formDocumentInfos = responseMessage.body?.formDocumentInfos || [];                    
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

    return {
        state,

        init,
        destroy,
        loadScreen,
        resetSearchCriteria,
        toListMode,
        toInfoMode,
        search
    };

};