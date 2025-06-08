import { BaseBody, HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import { Label } from "@/modules/common/model/label.model";
import { Language } from "@/modules/common/model/language.model";
import { UserProfile } from "@/modules/core/model/user-profile.model";
import httpClient from "@/modules/common/service/http/http-client.service";
import { Menu } from "@/modules/common/model/menu.model";
import { FavouriteMenu } from "@/modules/common/model/favourite-menu.model";

class WorkSpaceService
{
    public async loadWorkSpaceInfo(): Promise<LoadWorkSpaceInfoResponse>
    {
        console.info("### WorkSpaceService.loadWorkSpaceInfo ###");

        const apiEndpoint = '/api/core/home/web/load-workspace-info';
        const response = await httpClient.post<LoadWorkSpaceInfoResponse>(apiEndpoint, {});
        return response.data;
    }
}

interface LoadWorkSpaceInfoResponse extends HttpResponseMessage<{
    labels: Label[];
    payloadData: {
        userProfile: UserProfile;
        languages: Language[];
        currentLangCode: string;
        menus: Menu[];
        favouriteMenus: FavouriteMenu[];
    }
}> {};

export default new WorkSpaceService();