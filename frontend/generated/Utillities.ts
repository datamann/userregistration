import { EndpointRequestInit as EndpointRequestInit_1 } from "@hilla/frontend";
import client_1 from "./connect-client.default.js";
async function checkUser_1(init?: EndpointRequestInit_1): Promise<string | undefined> { return client_1.call("Utillities", "checkUser", {}, init); }
export { checkUser_1 as checkUser };
