import { RegisterRequest, UpdateProfileRequest, User } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;
export const getProfile = async (jwt: string) : Promise<User> => {
    if (!url || !jwt)   
        return ({
            firstName: "",
            lastName: "",
            email: "",
            role: "USER",
            phoneNumber: "",  
        })
    const res = await axios.get(`${url}/api/profile`, {
        headers: {
            Authorization: `Bearer ${jwt}`
        }
    })
    return res.data;
}

export const updateProfile = async (user: UpdateProfileRequest, jwt: string | null) : Promise<void> => {
    if (!url || !jwt)   
        return
    await axios.put(`${url}/api/profile`, user, {
        headers: {
            Authorization: `Bearer ${jwt}`
        }
    })
}