import { User } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;

export const getProfile = async (jwt: string) : Promise<User> => {
    if (!url)   
        throw new Error();
    const res = await axios.get(`${url}/api/profile`, {
        headers: {
            Authorization: `Bearer ${jwt}`
        }
    })
    return res.data;
}