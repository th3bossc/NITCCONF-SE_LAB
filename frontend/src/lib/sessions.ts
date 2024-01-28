import { Session, SessionRequest } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;

export const getAllSessions = async (jwt: string | null) : Promise<Session[] | void> => {
    if (!url || !jwt)
        return;
    const res = await axios.get<Session[]>(`${url}/api/session`, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })
    return res.data;
}

export const getSession = async (id: string, jwt: string | null) : Promise<Session | void> => {
    if (!url || !jwt)
        return;
    const res = await axios.get<Session>(`${url}/api/session/${id}`, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })  
    return res.data;
}

export const createSession = async (session: SessionRequest, jwt: string | null) : Promise<Session | void>  => {
    if (!url || !jwt) 
        return;
    const res = await axios.post<Session>(`${url}/api/session`, session, {
        headers: {
            Authorization: `Bearer ${jwt}`,
    }
    })
    return res.data;
}

export const uploadDoc = async (id: string, file: File, jwt: string | null) : Promise<number | void> => {
    if (!url || !jwt)
        return;
    const formData = new FormData();
    formData.append('file', file);
    const res = await axios.put(`${url}/api/session/doc/${id}`, formData, {
        headers: {
            Authorization: `Bearer ${jwt}`,
            'Content-Type': 'multipart/form-data',
        }
    })
    return res.status;
}
