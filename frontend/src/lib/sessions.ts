import { Session } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;
const tempToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWxqaXRoMjAwM0BnbWFpbC5jb20iLCJpYXQiOjE3MDYzNDM0MjUsImV4cCI6MTcwNjM3OTQyNX0.WcZgiZj1tnbvNYUwnzQUpA-pLynA59NxXOuQyeNYbUA";

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

