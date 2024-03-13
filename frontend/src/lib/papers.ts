import { Paper, PaperRequest } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;

export const getAllPapers = async (jwt: string | null): Promise<Paper[] | void> => {
    if (!url || !jwt)
        return;
    const res = await axios.get<Paper[]>(`${url}/api/paper`, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })
    return res.data;
}

export const getPaper = async (id: string, jwt: string | null): Promise<Paper | void> => {
    if (!url || !jwt)
        return;
    const res = await axios.get<Paper>(`${url}/api/paper/${id}`, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })
    return res.data;
}

export const createPaper = async (paper: PaperRequest, jwt: string | null): Promise<Paper | void> => {
    if (!url || !jwt)
        return;
    const res = await axios.post<Paper>(`${url}/api/paper`, paper, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })
    return res.data;
}

export const updatePaper = async (id: string, paper: PaperRequest, jwt: string | null): Promise<Paper | void> => {
    if (!url || !jwt)
        return;
    const res = await axios.put<Paper>(`${url}/api/paper/${id}`, paper, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })
    return res.data;
}

export const uploadDoc = async (id: string, file: File, jwt: string | null): Promise<number | void> => {
    if (!url || !jwt)
        return;
    const formData = new FormData();
    formData.append('file', file);
    const res = await axios.put(`${url}/api/paper/doc/${id}`, formData, {
        headers: {
            Authorization: `Bearer ${jwt}`,
            'Content-Type': 'multipart/form-data',
        }
    })
    return res.status;
}

export const deletePaper = async (id: string, jwt: string | null): Promise<void> => {
    if (!url || !jwt)
        return;
    await axios.delete(`${url}/api/paper/${id}`, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })
}
