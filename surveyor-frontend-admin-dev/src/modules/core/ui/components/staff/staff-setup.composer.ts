import { useAppStore } from "@/app/store/app.store";
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { StatusType } from "@/modules/common/model/http.response-message.model";
import EzAlert, { DialogResult } from "@/modules/common/ui/composables/ez-alert";
import { Role } from "@/modules/core/model/role.model";
import { StaffInfo } from "@/modules/core/model/staff-info.model";
import { UserGroup } from "@/modules/core/model/user-group.model";
import staffService, { CreateStaffRequestMessage, SearchStaffRequestMessage, SearchStaffResponseMessage, UpdateStaffRequestMessage } from "@/modules/core/service/staff.service";
import { defineStore } from "pinia";
import { reactive } from "vue";

const staffSetupStore = defineStore("core.staff.setup", () =>{
    console.info("### StaffSetupStore ###");

    const state = reactive ({
        mode: "list" as "list" | "entry",
        entryMode: "" as "add" | "edit" | "",
        
        searchCriteria: {
            staffName: ""
        },

        staffInfoForm: {
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
        staffInfos: [] as StaffInfo[]
    });

    function init()
    {
        console.log("### StaffSetupStore.init ###");
        resetState();
    }

    function destroy()
    {
        console.log("### StaffSetupStore.destroy ###");
        resetState();
    }

    function resetState() 
    {
        console.log("### StaffSetupStore.resetState ###");

        state.mode = "list";
        state.entryMode = "";
        state.searchCriteria = {
            staffName: ""
        };
        state.staffInfoForm = {
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
        state.staffInfos = [];
    }
  
    return {
        state,

        init,
        destroy
    };
});

export const useStaffSetupComposer = () => {
    const store = staffSetupStore();
    const state = store.state;
    const appStore = useAppStore();

    function init()
    {
        console.log("### StaffSetupComposor.init ###");
        store.init();
        loadScreen();
    }

    function destroy()
    {
        console.log("### StaffSetupComposor.destroy ###");
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
        console.log("### StaffSetupComposor.resetSearchCriteria ###");

        state.searchCriteria = {
            staffName: ""
        };
    };

    const loadScreen = async () => {
        console.log("### StaffSetupComposor.loadScreen ###");

        appStore.startLoading();
        try {
            const responseMessage = await staffService.loadScreen({});
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
        console.log("### StaffSetupComposor.search ###");

        state.staffInfos = [];

        const requestMessage: SearchStaffRequestMessage = {
            searchStaffRequest: {
                staffName: state.searchCriteria.staffName
            }
        };
        
        appStore.startLoading();
        try {
            const responseMessage: SearchStaffResponseMessage = await staffService.search(requestMessage);
            
            if(responseMessage.head?.status?.type === StatusType.Success) {
                state.staffInfos = responseMessage.body?.searchStaffResponse?.staffInfos || [];

                if(state.searchCriteria.staffName !== "" && state.staffInfos.length !== 0) {
                    state.staffInfos = state.staffInfos.filter((staffInfo: StaffInfo) => {
                        return staffInfo.user.accountName?.toLocaleLowerCase().includes(state.searchCriteria.staffName.toLowerCase());
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
        console.log("### StaffSetupComposor.validateForm ###");

        if(state.staffInfoForm.username === "") {
            EzAlert.showError({
                title: "Error",
                text: "Username is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.staffInfoForm.email === "") {
            EzAlert.showError({
                title: "Error",
                text: "Email is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.staffInfoForm.accountName === "") {
            EzAlert.showError({
                title: "Error",
                text: "Account Name is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.staffInfoForm.roleIds.length === 0) {
            EzAlert.showError({
                title: "Error",
                text: "User Role is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.staffInfoForm.userGroupIds.length === 0) {
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
        console.log("### StaffSetupComposor.save ###");

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
        console.log("### StaffSetupComposor.create ###");

        const requestMessage: CreateStaffRequestMessage = {
            createStaffRequest: {
                username: state.staffInfoForm.username,
                email: state.staffInfoForm.email,
                accountName: state.staffInfoForm.accountName,
                roleIds: state.staffInfoForm.roleIds,
                userGroupIds: state.staffInfoForm.userGroupIds
            }
        };

        appStore.startLoading();
        try {
            const responseMessage = await staffService.create(requestMessage);
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
        console.log("### StaffSetupComposor.update ###");

        const requestMessage: UpdateStaffRequestMessage = {
            updateStaffRequest: {
                userId: state.staffInfoForm.userId,
                username: state.staffInfoForm.username,
                email: state.staffInfoForm.email,
                accountName: state.staffInfoForm.accountName,
                roleIds: state.staffInfoForm.roleIds,
                userGroupIds: state.staffInfoForm.userGroupIds,
                status: state.staffInfoForm.status
            }
        };

        appStore.startLoading();
        try {
            const responseMessage = await staffService.update(requestMessage);
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
        console.log("### StaffSetupComposor.resetForm ###");

        state.staffInfoForm = {
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