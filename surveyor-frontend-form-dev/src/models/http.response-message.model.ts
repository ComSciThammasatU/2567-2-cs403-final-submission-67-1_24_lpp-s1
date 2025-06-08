export interface HttpResponseMessage <BODY extends BaseBody>
{
    head: Head;
    body?: BODY;
}

export interface Head {
    traceId: string;
    status: Status;
    secretProtocol?: SecretProtocol<any>;
    metadatas: Metadata[];
};

export interface Status {
    type: StatusType;
    code: string;
    // message: StatusMessage;
    title: string;
    message: string;
    // error?: Error;
};

export enum StatusType {
    Success = "Success", 
    Error = "Error"
}

export interface StatusMessage {
    displayType: DisplayType;
    title: string;
    content: string;
    buttons: Button[];
};

export enum DisplayType {
    Popup = "Popup", 
    Alert = "Alert", 
    Toast = "Toast"
}

export interface Button {
    type: string;
    text: string;
}

export interface Error {
    from: ErrorFrom;
    exception?: Exception;
}

export enum ErrorFrom {
    Client = "Client", 
    Server = "Server", 
    Unknown = "Unknown"
}

export interface Exception {
    type: string;
    message: string;
    stacktrace: string;
}

export interface SecretProtocol <PAYLOAD> {
    operationCode: string;
    operationPayload: PAYLOAD;
};

export interface Metadata {
    attributeName: string;
    attributeValue: any;
}

export interface BaseBody {};