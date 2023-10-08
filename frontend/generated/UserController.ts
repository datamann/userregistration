import { EndpointRequestInit as EndpointRequestInit_1 } from "@hilla/frontend";
import client_1 from "./connect-client.default.js";
import type User_1 from "./no/sivertsensoftware/userregistration/model/User.js";
async function createUser_1(user: User_1 | undefined, init?: EndpointRequestInit_1): Promise<User_1 | undefined> { return client_1.call("UserController", "createUser", { user }, init); }
async function deleteById_1(id: number | undefined, init?: EndpointRequestInit_1): Promise<boolean> { return client_1.call("UserController", "deleteById", { id }, init); }
async function findAll_1(init?: EndpointRequestInit_1): Promise<Array<User_1 | undefined> | undefined> { return client_1.call("UserController", "findAll", {}, init); }
async function findByEmail_1(email: string | undefined, init?: EndpointRequestInit_1): Promise<Array<User_1 | undefined> | undefined> { return client_1.call("UserController", "findByEmail", { email }, init); }
async function findByID_1(id: number | undefined, init?: EndpointRequestInit_1): Promise<User_1 | undefined> { return client_1.call("UserController", "findByID", { id }, init); }
async function findByLastname_1(last_name: string | undefined, init?: EndpointRequestInit_1): Promise<Array<User_1 | undefined> | undefined> { return client_1.call("UserController", "findByLastname", { last_name }, init); }
async function isAdmin_1(init?: EndpointRequestInit_1): Promise<boolean> { return client_1.call("UserController", "isAdmin", {}, init); }
async function update_1(user: User_1 | undefined, id: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("UserController", "update", { user, id }, init); }
export { createUser_1 as createUser, deleteById_1 as deleteById, findAll_1 as findAll, findByEmail_1 as findByEmail, findByID_1 as findByID, findByLastname_1 as findByLastname, isAdmin_1 as isAdmin, update_1 as update };
