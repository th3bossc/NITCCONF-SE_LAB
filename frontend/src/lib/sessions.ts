import { Session } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;
const tempToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWxqaXRoMjAwM0BnbWFpbC5jb20iLCJpYXQiOjE3MDYyNzk1MTksImV4cCI6MTcwNjMxNTUxOX0.ktomQSBsK66U0yTvWmPCUEjOONrf7RabnwFJeS_jXw4";

export const getAllSessions = async () : Promise<Session[]> => {
    if (!url)
        throw new Error()
    const res = await axios.get(`${url}/api/session`, {
        headers: {
            Authorization: `Bearer ${tempToken}`,
        }
    })
    return res.data;
}

export const getSession = async (id: string) : Promise<Session> => {
    if (!url)
        throw new Error()
    const res = await axios.get(`${url}/api/session/${id}`, {
        headers: {
            Authorization: `Bearer ${tempToken}`,
        }
    })  
    return res.data;
}

