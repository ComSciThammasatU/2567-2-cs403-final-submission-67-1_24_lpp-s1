import { HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import { UserGroup } from "../model/user-group.model";
import { Role } from "../model/role.model";
import httpClient from "@/modules/common/service/http/http-client.service";
import { StudentInfo } from "../model/student-info.model";
import StatusCodeConst from "@/modules/common/const/status-code.const";


class StudentService
{
    public async loadScreen(requestMessage: StudentLoadScreenRequestMessage): Promise<StudentLoadScreenResponseMessage> 
    {
        console.info("### StudentService.loadScreen ###");
        const apiEndpoint = '/api/core/student/setup/load-screen';
        const response = await httpClient.post<StudentLoadScreenResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async search(requestMessage: StudentSearchRequestMessage): Promise<StudentSearchResponseMessage> 
    {
        console.info("### StudentService.search ###");
        const apiEndpoint = '/api/core/student/setup/search';
        const response = await httpClient.post<StudentSearchResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async loadInfo(requestMessage: StudentLoadInfoRequestMessage): Promise<StudentLoadInfoResponseMessage> 
    {
        console.info("### StudentService.loadInfo ###");
        const apiEndpoint = '/api/core/student/setup/load-info';
        const response = await httpClient.post<StudentLoadInfoResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async update(requestMessage: StudentUpdateRequestMessage): Promise<StudentUpdateResponseMessage> 
    {
        console.info("### StudentService.update ###");
        const apiEndpoint = '/api/core/student/setup/update';
        const response = await httpClient.post<StudentUpdateResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}


// ****************************** load-screen ****************************** //
export interface StudentLoadScreenRequestMessage {
    
};

export interface StudentLoadScreenResponseMessage extends HttpResponseMessage<{
    roles: Role[];
    userGroups: UserGroup[];
}> {};
// ****************************** load-screen ****************************** //


// ****************************** search ****************************** //
export interface StudentSearchRequestMessage {
    searchStudentRequest?: {
        criteria?: {
            studentCodes?: string[];
            studentName?: string;
        }
    };
};

export interface StudentSearchResponseMessage extends HttpResponseMessage<{
    searchStudentResponse?: {
        studentInfos: StudentInfo[];
    }
}> {};
// ****************************** search ****************************** //


// ****************************** load-info ****************************** //
export interface StudentLoadInfoRequestMessage {
    studentId: string;
};

export interface StudentLoadInfoResponseMessage extends HttpResponseMessage<{
    studentInfo: StudentInfo;
}> {};
// ****************************** load-info ****************************** //


// ****************************** update ****************************** //
export interface StudentUpdateRequestMessage {
    updateStudentRequest: {
        studentId: string;
        studentCode: string;
        studyDegreeId: string;
        studyClassLevel: number;

        userId: string;
        username: string;
        email: string;
        accountName: string;

        userGroupIds: string[];

        status: StatusCodeConst;
    };
};

export interface StudentUpdateResponseMessage extends HttpResponseMessage<{
    updateStudentResponse?: {
        studentInfo: StudentInfo;
    }
}> {};
// ****************************** update ****************************** //


export default new StudentService();