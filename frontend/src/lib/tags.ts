import { Tag } from '@/types';
import axios from 'axios';


const url = process.env.NEXT_PUBLIC_BACKEND_URL;

export const getTags = async (jwt : string | null) : Promise<Tag[] | void> => {
    if (!url || !jwt)
        return;
    const res = await axios.get<Tag[]>(`${url}/api/tags`, {
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    });

    return res.data;
}