import { LoginRequest, RegisterRequest } from '@/types';
import axios from 'axios';

const url = process.env.NEXT_PUBLIC_BACKEND_URL;

export const register = async (data : RegisterRequest) : Promise<{
  token : string
}> => {
    const response = await axios.post(`${url}/api/auth/register`, data, {
    headers: {
      "Content-Type": "application/json",
    }
  })
  return response.data;
}

export const login = async (data : LoginRequest) : Promise<{
  token : string
}> => {
  const response = await axios.post(`${url}/api/auth/login`, data, {
    headers: {
      "Content-Type": "application/json",
    }
  })
  return response.data;
}