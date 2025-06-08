import { useAppStore } from "@/app/store/app.store";
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { StatusType } from "@/modules/common/model/http.response-message.model";
import EzAlert, { DialogResult } from "@/modules/common/ui/composables/ez-alert";
import { Role } from "@/modules/core/model/role.model";
import { StudentInfo } from "@/modules/core/model/student-info.model";
import { UserGroup } from "@/modules/core/model/user-group.model";
import studentService, { StudentUpdateResponseMessage, StudentUpdateRequestMessage, StudentSearchRequestMessage, StudentSearchResponseMessage } from "@/modules/core/service/student.service";
import { defineStore } from "pinia";
import { reactive } from "vue";

const studentSetupStore = defineStore("core.student.setup", () =>{
    console.info("### StudentSetupStore ###");

    const state = reactive ({
        mode: "list" as "list" | "entry",
        entryMode: "" as "add" | "edit" | "",
        
        searchCriteria: {
            studentCode: "",
            studentName: ""
        },

        studentInfoForm: {
            studentId: "" as string,
            studentCode: "" as string,
            studyDegreeId: "" as string,
            studyClassLevel: 0 as number,
            userId: "" as string,
            username: "" as string,
            email: "" as string,
            accountName: "" as string,
            userGroupIds: [] as string[],
            status: "" as StatusCodeConst
        },

        roles: [] as Role[],
        userGroups: [] as UserGroup[],
        studentInfos: [] as StudentInfo[]
    });

    function init()
    {
        console.log("### StudentSetupStore.init ###");
        resetState();
    }

    function destroy()
    {
        console.log("### StudentSetupStore.destroy ###");
        resetState();
    }

    function resetState() 
    {
        console.log("### StudentSetupStore.resetState ###");

        state.mode = "list";
        state.entryMode = "";
        state.searchCriteria = {
            studentCode: "",
            studentName: ""
        };
        state.studentInfoForm = {
            studentId: "",
            studentCode: "",
            studyDegreeId: "",
            studyClassLevel: 0,
            userId: "",
            username: "",
            email: "",
            accountName: "",
            userGroupIds: [],
            status: StatusCodeConst.Active
        };
        state.roles = [];
        state.userGroups = [];
        state.studentInfos = [];
    }
  
    return {
        state,

        init,
        destroy
    };
});

export const useStudentSetupComposer = () => {
    const store = studentSetupStore();
    const state = store.state;
    const appStore = useAppStore();

    function init()
    {
        console.log("### StudentSetupComposor.init ###");
        store.init();
        loadScreen();
    }

    function destroy()
    {
        console.log("### StudentSetupComposor.destroy ###");
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
        console.log("### StudentSetupComposor.resetSearchCriteria ###");

        state.searchCriteria = {
            studentCode: "",
            studentName: ""
        };
    };

    const loadScreen = async () => {
        console.log("### StudentSetupComposor.loadScreen ###");

        appStore.startLoading();
        try {
            const responseMessage = await studentService.loadScreen({});
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
        console.log("### StudentSetupComposor.search ###");

        state.studentInfos = [];

        const requestMessage: StudentSearchRequestMessage = {
            searchStudentRequest: {
                criteria: {
                    studentCodes: [state.searchCriteria.studentCode],
                    studentName: state.searchCriteria.studentName
                }
            }
        };
        
        appStore.startLoading();
        try {
            const responseMessage: StudentSearchResponseMessage = await studentService.search(requestMessage);
            
            if(responseMessage.head?.status?.type === StatusType.Success) {
                state.studentInfos = responseMessage.body?.searchStudentResponse?.studentInfos || [];

                if(state.searchCriteria.studentCode !== "" && state.studentInfos.length !== 0) {
                    state.studentInfos = state.studentInfos.filter((studentInfo: StudentInfo) => {
                        return studentInfo.student.studentCode?.toLocaleLowerCase().includes(state.searchCriteria.studentCode.toLowerCase());
                    });
                }

                if(state.searchCriteria.studentName !== "" && state.studentInfos.length !== 0) {
                    state.studentInfos = state.studentInfos.filter((studentInfo: StudentInfo) => {
                        return studentInfo.user.accountName?.toLocaleLowerCase().includes(state.searchCriteria.studentName.toLowerCase());
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
        console.log("### StudentSetupComposor.validateForm ###");

        if(state.studentInfoForm.studentId === "") {
            EzAlert.showError({
                title: "Error",
                text: "Student Id is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.studentInfoForm.studentCode === "") {
            EzAlert.showError({
                title: "Error",
                text: "Student Code is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.studentInfoForm.studyDegreeId === "") {
            EzAlert.showError({
                title: "Error",
                text: "Study Degree is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.studentInfoForm.studyClassLevel <= 0 || state.studentInfoForm.studyClassLevel >= 10) {
            EzAlert.showError({
                title: "Error",
                text: "Invalid Study Class Level.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.studentInfoForm.username === "") {
            EzAlert.showError({
                title: "Error",
                text: "Username is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.studentInfoForm.email === "") {
            EzAlert.showError({
                title: "Error",
                text: "Email is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.studentInfoForm.accountName === "") {
            EzAlert.showError({
                title: "Error",
                text: "Account Name is required.",
                buttonText: "OK",
            });
            return false;
        }

        if(state.studentInfoForm.userGroupIds.length === 0) {
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
        console.log("### StudentSetupComposor.save ###");

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
                        // do nothing
                    } else if(state.entryMode === "edit") {
                        update();
                    }
                }
            }
        });
    };

    const update = async () => {
        console.log("### StudentSetupComposor.update ###");

        const requestMessage: StudentUpdateRequestMessage = {
            updateStudentRequest: {
                studentId: state.studentInfoForm.studentId,
                studentCode: state.studentInfoForm.studentCode,
                studyDegreeId: state.studentInfoForm.studyDegreeId,
                studyClassLevel: state.studentInfoForm.studyClassLevel,
                userId: state.studentInfoForm.userId,
                username: state.studentInfoForm.username,
                email: state.studentInfoForm.email,
                accountName: state.studentInfoForm.accountName,
                userGroupIds: state.studentInfoForm.userGroupIds,
                status: state.studentInfoForm.status
            }
        };

        appStore.startLoading();
        try {
            const responseMessage: StudentUpdateResponseMessage = await studentService.update(requestMessage);
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
        console.log("### StudentSetupComposor.resetForm ###");

        state.studentInfoForm = {
            studentId: "",
            studentCode: "",
            studyDegreeId: "",
            studyClassLevel: 0,
            userId: "",
            username: "",
            email: "",
            accountName: "",
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