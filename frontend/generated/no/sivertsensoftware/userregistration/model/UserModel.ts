import { _getPropertyModel as _getPropertyModel_1, Email as Email_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, NumberModel as NumberModel_1, ObjectModel as ObjectModel_1, Size as Size_1, StringModel as StringModel_1 } from "@hilla/form";
import type User_1 from "./User.js";
class UserModel<T extends User_1 = User_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(UserModel);
    get id(): NumberModel_1 {
        return this[_getPropertyModel_1]("id", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Long" } }));
    }
    get first_name(): StringModel_1 {
        return this[_getPropertyModel_1]("first_name", (parent, key) => new StringModel_1(parent, key, true, { validators: [new Size_1({ min: 2, max: 50, message: "First name must be between 2 and 50 characters" })], meta: { javaType: "java.lang.String" } }));
    }
    get last_name(): StringModel_1 {
        return this[_getPropertyModel_1]("last_name", (parent, key) => new StringModel_1(parent, key, true, { validators: [new Size_1({ min: 2, max: 50, message: "Last name must be between 2 and 50 characters" })], meta: { javaType: "java.lang.String" } }));
    }
    get email(): StringModel_1 {
        return this[_getPropertyModel_1]("email", (parent, key) => new StringModel_1(parent, key, true, { validators: [new Email_1()], meta: { javaType: "java.lang.String" } }));
    }
}
export default UserModel;
