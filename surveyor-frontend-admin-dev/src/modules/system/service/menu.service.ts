import httpClient from '@/modules/common/service/http/http-client.service';
import { HttpResponseMessage } from '@/modules/common/model/http.response-message.model';
import { Menu } from "@/modules/common/model/menu.model";
import { FavouriteMenu } from '@/modules/common/model/favourite-menu.model';

class MenuService
{
    public async loadMyMenus(): Promise<LoadMyMenuResponse>
    {
        const apiEndpoint = '/api/system/menu/load-my-menu';
        const response = await httpClient.post<LoadMyMenuResponse>(apiEndpoint, {});
        return response.data;
    }

    public async loadMyFavouriteMenus(): Promise<LoadMyFavouriteMenuResponse>
    {
        const apiEndpoint = '/api/system/menu/load-my-favourite-menu';
        const response = await httpClient.post<LoadMyFavouriteMenuResponse>(apiEndpoint, {});
        return response.data;
    }

    public async addMyFavouriteMenu(requestMessage: AddMyFavouriteMenuRequest): Promise<AddMyFavouriteMenuResponse>
    {
        const apiEndpoint = '/api/system/menu/add-my-favourite-menu';
        const response = await httpClient.post<AddMyFavouriteMenuResponse>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async removeMyFavouriteMenu(requestMessage: RemoveMyFavouriteMenuRequest): Promise<RemoveMyFavouriteMenuResponse>
    {
        const apiEndpoint = '/api/system/menu/remove-my-favourite-menu';
        const response = await httpClient.post<RemoveMyFavouriteMenuResponse>(apiEndpoint, requestMessage);
        return response.data;
    }
}


export interface LoadMyMenuResponse extends HttpResponseMessage<{
    menus: Menu[];
}> {};


export interface LoadMyFavouriteMenuResponse extends HttpResponseMessage<{
    favouriteMenus: FavouriteMenu[];
}> {};


export interface AddMyFavouriteMenuRequest {
    addMyFavouriteMenuRequest: {
        menuId: string;
        seqNo: number;
    }
}


export interface AddMyFavouriteMenuResponse extends HttpResponseMessage<{
    addedFavouriteMenu: FavouriteMenu;
}> {};


export interface RemoveMyFavouriteMenuRequest {
    favouriteMenuId: string;
};


export interface RemoveMyFavouriteMenuResponse extends HttpResponseMessage<{
    removedFavouriteMenu: FavouriteMenu;
}> {};


export default new MenuService();