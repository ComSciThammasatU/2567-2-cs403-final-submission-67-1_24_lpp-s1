import { AxiosResponse } from 'axios';
import { Role } from "@/modules/core/model/role.model";
import httpClient from "@/modules/common/service/http/http-client.service";

class RoleService
{
    public fetchAll(): Promise<AxiosResponse<Role[], any>>
    {
        return httpClient.get("/api/role/fetch-all");
    }
}

export default new RoleService();