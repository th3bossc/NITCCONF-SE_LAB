import { AuthContext } from "@/AuthContext";
import { AuthDetails } from "@/types";
import { useContext } from "react";

export const useAuthContext = () : AuthDetails => {
    const context = useContext(AuthContext);
    if (context === null)
        throw new Error("AuthContext is null");
    else
        return context;
}