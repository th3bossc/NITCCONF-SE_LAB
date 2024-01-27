import { Session } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;

export const getAllSessions = async (jwt: string | null) : Promise<Session[]> => {
    if (!url || !jwt)
        throw new Error()
    const res = await axios.get(`${url}/api/session`, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })
    return res.data;
}

export const getSession = async (id: string, jwt: string | null) : Promise<Session> => {
    if (!url || !jwt)
        throw new Error()
    const res = await axios.get(`${url}/api/session/${id}`, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })  
    return res.data;
}

