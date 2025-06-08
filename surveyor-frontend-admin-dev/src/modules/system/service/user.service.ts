import { BaseBody, HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import { User } from "@/modules/core/model/user.model";
import { UserProfile } from "@/modules/core/model/user-profile.model";
import httpClient from "@/modules/common/service/http/http-client.service";

class UserService
{
    protected users: User[];

    constructor() {
        this.users = [
            {
                id: 'u-001',
                username: 'alis',
                password: '1234'
            },

            {
                id: 'u-002',
                username: 'bob',
                password: '9999'
            }
        ];
    }

    public loadAll(): Promise<User[]> {
        return new Promise((resolve, reject) => {
            resolve(this.users);
        });
    }

    public async loadCurrentUserProfile(): Promise<UserProfile | undefined>
    {
        console.log("### UserService.loadCurrentUserProfile ###");

        const apiEndpoint = '/api/system/user/get-current-user-profile';
        const response = await httpClient.post<HttpResponseMessage<GetCurrentUserProfileBodyResponseMessage>>(apiEndpoint);
        return response.data.body?.compactUserProfile;
    }
}

interface GetCurrentUserProfileBodyResponseMessage extends BaseBody
{
    compactUserProfile: UserProfile;
}

const userService = new UserService();

export default userService;