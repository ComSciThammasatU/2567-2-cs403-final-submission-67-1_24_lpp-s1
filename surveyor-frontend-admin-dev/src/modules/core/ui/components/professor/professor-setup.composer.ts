import { useAppStore } from "@/app/store/app.store";
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { StatusType } from "@/modules/common/model/http.response-message.model";
import EzAlert, { DialogResult } from "@/modules/common/ui/composables/ez-alert";
import { Role } from "@/modules/core/model/role.model";
import { ProfessorInfo } from "@/modules/core/model/professor-info.model";
import { UserGroup } from "@/modules/core/model/user-group.model";
import professorService, { CreateProfessorRequestMessage, SearchProfessorRequestMessage, SearchProfessorResponseMessage, UpdateProfessorRequestMessage } from "@/modules/core/service/professor.service";
import { defineStore } from "pinia";
import { reactive } from "vue";

const professorSetupStore = defineStore("core.professor.setup", () =>{
    console.info("### ProfessorSetupStore ###");

    const state = reactive ({
        mode: "list" as "list" | "entry",
        entryMode: "" as "add" | "edit" | "",
        
        searchCriteria: {
            professorName: ""
        },

        professorInfoForm: {
            userId: "" as string,
            username: "" as string,
            email: "" as string,
            accountName: "" as string,
            roleIds: [] as string[],
            userGroupIds: [] as string[],
            status: "" as StatusCodeConst
        },

        roles: [] as Role[],
        userGroups: [] as UserGroup[],
        professorInfos: [] as ProfessorInfo[]
    });

    function init()
    {
        console.log("### ProfessorSetupStore.init ###");
        resetState();
    }

    function destroy()
    {
        console.log("### ProfessorSetupStore.destroy ###");
        resetState();
    }

    function resetState() 
    {
        console.log("### ProfessorSetupStore.resetState ###");

        state.mode = "list";
        state.entryMode = "";
        state.searchCriteria = {
            professorName: ""
        };
        state.professorInfoForm = {
            userId: "",
            username: "",
            email: "",
            accountName: "",
            roleIds: [],
            userGroupIds: [],
            status: StatusCodeConst.Active
        };
        state.roles = [];
        state.userGroups = [];
        state.professorInfos = [];
    }
  
    return {
        state,

        init,
        destroy
    };
});

export const useProfessorSetupComposer = () => {
    const store = professorSetupStore();
    const state = store.state;
    const appStore = useAppStore();

    function init()
    {
        console.log("### ProfessorSetupComposor.init ###");
        store.init();
        loadScreen();
    }

    function destroy()
    {
        console.log("### ProfessorSetupComposor.destroy ###");
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

    const resetSearchCriteria = () => {
        console.log("### ProfessorSetupComposor.resetSearchCriteria ###");

        state.searchCriteria = {
            professorName: ""
        };
    };

    const loadScreen = async () => {
        console.log("### ProfessorSetupComposor.loadScreen ###");

        appStore.startLoading();
        try {
            const responseMessage = await professorService.loadScreen({});
            state.roles = responseMessage.body?.roles || [];
            state.userGroups = responseMessage.body?.userGroups || [];
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
        console.log("### ProfessorSetupComposor.search ###");

        state.professorInfos = [];

        const requestMessage: SearchProfessorRequestMessage = {
            searchProfessorRequest: {
                professorName: state.searchCriteria.professorName
            }
        };
        
        appStore.startLoading();
        try {
            const responseMessage: SearchProfessorResponseMessage = await professorService.search(requestMessage);
            console.log(responseMessage);

            if(responseMessage.head?.status?.type === StatusType.Success) {
                state.professorInfos = responseMessage.body?.searchProfessorResponse?.professorInfos || [];

                if(state.searchCriteria.professorName !== "" && state.professorInfos.length !== 0) {
                    state.professorInfos = state.professorInfos.filter((professorInfo: ProfessorInfo) => {
                        return professorInfo.user.accountName?.toLocaleLowerCase().includes(state.searchCriteria.professorName.toLowerCase());
                    });
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

    const validateForm = (): boolean => {
        console.log("### ProfessorSetupComposor.validateForm ###");

        if(state.professorInfoForm.username === "") {
            EzAlert.showError({
                title: "Error",
                text: "Username is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.professorInfoForm.email === "") {
            EzAlert.showError({
                title: "Error",
                text: "Email is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.professorInfoForm.accountName === "") {
            EzAlert.showError({
                title: "Error",
                text: "Account Name is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.professorInfoForm.roleIds.length === 0) {
            EzAlert.showError({
                title: "Error",
                text: "User Role is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.professorInfoForm.userGroupIds.length === 0) {
            EzAlert.showError({
                title: "Error",
                text: "User Group is required.",
                buttonText: "OK",
            });
            return false;
        }

        return true;
    };

    const save = () => {
        console.log("### ProfessorSetupComposor.save ###");

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
        console.log("### ProfessorSetupComposor.create ###");

        const requestMessage: CreateProfessorRequestMessage = {
            createProfessorRequest: {
                username: state.professorInfoForm.username,
                email: state.professorInfoForm.email,
                accountName: state.professorInfoForm.accountName,
                roleIds: state.professorInfoForm.roleIds,
                userGroupIds: state.professorInfoForm.userGroupIds
            }
        };

        appStore.startLoading();
        try {
            const responseMessage = await professorService.create(requestMessage);
            console.log(responseMessage);

            if(responseMessage.head?.status?.type === StatusType.Success) {
                EzAlert.showSuccess({
                    title: responseMessage.head?.status?.title,
                    text: responseMessage.head?.status?.message,
                    buttonText: "OK",
                });

                toListMode();
                resetForm();
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

    const update = async () => {
        console.log("### ProfessorSetupComposor.update ###");

        const requestMessage: UpdateProfessorRequestMessage = {
            updateProfessorRequest: {
                userId: state.professorInfoForm.userId,
                username: state.professorInfoForm.username,
                email: state.professorInfoForm.email,
                accountName: state.professorInfoForm.accountName,
                roleIds: state.professorInfoForm.roleIds,
                userGroupIds: state.professorInfoForm.userGroupIds,
                status: state.professorInfoForm.status
            }
        };

        appStore.startLoading();
        try {
            const responseMessage = await professorService.update(requestMessage);
            console.log(responseMessage);

            if(responseMessage.head?.status?.type === StatusType.Success) {
                EzAlert.showSuccess({
                    title: responseMessage.head?.status?.title,
                    text: responseMessage.head?.status?.message,
                    buttonText: "OK",
                });

                toListMode();
                resetForm();
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

    const resetForm = () => {
        console.log("### ProfessorSetupComposor.resetForm ###");

        state.professorInfoForm = {
            userId: "",
            username: "",
            email: "",
            accountName: "",
            roleIds: [],
            userGroupIds: [],
            status: StatusCodeConst.Active
        };
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
        search,
        save,
        resetForm
    };

};