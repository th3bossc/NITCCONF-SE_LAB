import { LoginRequest, RegisterRequest } from '@/types';
import axios from 'axios';

export const register = async (data : RegisterRequest) : Promise<{
  token : string
}> => {
    const jsonData = JSON.stringify(data);
    const config = {
      method: 'post',
      maxBodyLength: Infinity,
      url: 'http://localhost:8080/api/auth/register',
      headers: { 
        'Content-Type': 'application/json',
      },
      data : jsonData
    };
    

    const response = await axios(config);
    return response.data;
}

export const login = async (data : LoginRequest) : Promise<{
  token : string
}> => {
    const jsonData = JSON.stringify(data);

    const config = {
        method: 'post',
        maxBodyLength: Infinity,
        url: 'http://localhost:8080/api/auth/login',
        headers: { 
          'Content-Type': 'application/json',
        },
        data : jsonData
      };
      
  
      const response = await axios(config);
      return response.data;
}