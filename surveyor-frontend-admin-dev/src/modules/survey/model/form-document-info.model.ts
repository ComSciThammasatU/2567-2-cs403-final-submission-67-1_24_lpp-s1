import { User } from "@/modules/core/model/user.model";
import type { FormDocumentElement } from "./form-document-element.model";
import type { FormDocument } from "./form-document.model";
import type { FormTemplateInfo } from "./form-template-info.model";

export interface FormDocumentInfo {
    formDocument: FormDocument;
    formDocumentElements: FormDocumentElement[];
    formTemplateInfo?: FormTemplateInfo;

    submittedUser: User;
};