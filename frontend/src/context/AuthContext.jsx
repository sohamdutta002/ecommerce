import { createContext, useReducer } from "react";
import { authReducer, initialAuthState } from "./AuthReducer";

export const AuthContext=createContext();
export const AuthProvider=({children})=>{
    const [authState,dispatch]=useReducer(authReducer,initialAuthState);
    return (
        <AuthContext.Provider value={{authState,dispatch}}>
            {children}
        </AuthContext.Provider>
    )
}