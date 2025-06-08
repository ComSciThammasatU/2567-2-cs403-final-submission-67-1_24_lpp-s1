import type { HttpResponseMessage } from "../models/http.response-message.model";
import httpClient from "./http/http-client.service";
import type { Profile as LineUserProfile } from '@liff/get-profile';

class StudentService
{
    public async loadRegisterScreen(requestMessage: StudentRegisterLoadScreenRequestMessage): Promise<StudentRegisterLoadScreenResponseMessage> 
    {
        console.info("### StudentService.loadRegisterScreen ###");
        const apiEndpoint = '/api/core/student/register/load-screen';
        const response = await httpClient.post<StudentRegisterLoadScreenResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async submit(requestMessage: StudentRegisterRequestMessage): Promise<StudentRegisterResponseMessage>
    {
        console.info("### StudentService.submit ###");
        const apiEndpoint = '/api/core/student/register/submit';
        const response = await httpClient.post<StudentRegisterResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}

export interface StudentRegisterLoadScreenRequestMessage {
    
};

export interface StudentRegisterLoadScreenResponseMessage extends HttpResponseMessage<{
    
}> {};

export interface StudentRegisterRequestMessage {
    registerStudentRequest: {
        studentCode: string;
        accountName: string;
        email: string;
        username: string;
        password: string;
        studyDegreeId: string;
        studyClassLevel: string;

        lineUserProfile?: LineUserProfile;
    }
};

export interface StudentRegisterResponseMessage extends HttpResponseMessage<{
    registerStudentResponse: {

    }
}> {};

export default new StudentService();