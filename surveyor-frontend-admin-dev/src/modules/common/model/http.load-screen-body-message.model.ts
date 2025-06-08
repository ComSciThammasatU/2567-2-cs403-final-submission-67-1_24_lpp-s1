import { GrantedAuthority } from "./granted-authority.model";
import { BaseBody } from "./http.response-message.model";
import { Label } from "./label.model";

export interface HttpLoadScreenBodyResponseMessage <PAYLOAD> extends BaseBody {
    shouldAuthen: boolean;
    shouldAuthorize: boolean;
    grantedAuthority?: GrantedAuthority;
    labels: Label[];
    payloadData: PAYLOAD;
};