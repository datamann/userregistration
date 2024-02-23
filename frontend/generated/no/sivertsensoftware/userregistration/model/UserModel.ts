import { _getPropertyModel as _getPropertyModel_1, Email as Email_1, NumberModel as NumberModel_1, ObjectModel as ObjectModel_1, Size as Size_1, StringModel as StringModel_1 } from "@hilla/form";
import type User_1 from "./User.js";
class UserModel<T extends User_1 = User_1> extends ObjectModel_1<T> {
    declare static createEmptyValue: () => User_1;
    get email(): StringModel_1 {
        return this[_getPropertyModel_1]("email", StringModel_1, [true, new Email_1()]) as StringModel_1;
    }
    get first_name(): StringModel_1 {
        return this[_getPropertyModel_1]("first_name", StringModel_1, [true, new Size_1({ min: 2, max: 50, message: "First name must be between 2 and 50 characters" })]) as StringModel_1;
    }
    get id(): NumberModel_1 {
        return this[_getPropertyModel_1]("id", NumberModel_1, [true]) as NumberModel_1;
    }
    get last_name(): StringModel_1 {
        return this[_getPropertyModel_1]("last_name", StringModel_1, [true, new Size_1({ min: 2, max: 50, message: "Last name must be between 2 and 50 characters" })]) as StringModel_1;
    }
}
export default UserModel;
