import { FormTemplateAuthority } from "./form-template-authority.mode";
import { FormTemplateElement } from "./form-template-element.model";
import { FormTemplate } from "./form-template.model";

export interface FormTemplateInfo {
    formTemplate?: FormTemplate;
    formTemplateElements?: FormTemplateElement[];
    formTemplateAuthorities?: FormTemplateAuthority[];
};