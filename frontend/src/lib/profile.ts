import { User } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;
const jwt = localStorage.getItem('jwt');
export const getProfile = async (jwt: string) : Promise<User> => {
    if (!url || !jwt)   
        throw new Error();
    const res = await axios.get(`${url}/api/profile`, {
        headers: {
            Authorization: `Bearer ${jwt}`
        }
    })
    return res.data;
}

export const updateProfile = async (user: User) : Promise<User> => {
    if (!url || !jwt)   
        throw new Error();
    const res = await axios.put(`${url}/api/profile`, user, {
        headers: {
            Authorization: `Bearer ${jwt}`
        }
    })
    return res.data;
}