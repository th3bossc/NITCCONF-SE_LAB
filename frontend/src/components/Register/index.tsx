"use client"
import React, { useEffect } from "react";
import "./index.css";
import AnimatedButton from "../AnimatedButton/index";
import axios from "axios";


const Register = ()=>
{
    const [changedFName,setChangedFName]=React.useState(false);
    const [fName,setFName]=React.useState("");
    const [changedLName,setChangedLName]=React.useState(false);
    const [lName,setLName]=React.useState("");
    const [changedUser,setChangedUser]=React.useState(false);
    const [username,setUser]=React.useState("");
    const [changedPass,setChangedPass]=React.useState(false);
    const [pass,setPass]=React.useState("");
    const [changedPhone,setChangedPhone]=React.useState(false);
    const [phone,setPhone]=React.useState("");
    const [fNameRed,setFNameRed]=React.useState(false);
    const [lNameRed,setLNameRed]=React.useState(false);
    const [userRed,setUserRed]=React.useState(false);
    const [passRed,setPassRed]=React.useState(false);
    const [phoneRed,setPhoneRed]=React.useState(false);

    useEffect(()=>
    {
        if(changedFName && fName=="")
        {
            setFNameRed(true);
        }
        else
        {
            setFNameRed(false);
        }
    },[fName])

    useEffect(()=>
    {
        if(changedLName && lName=="")
        {
            setLNameRed(true);
        }
        else
        {
            setLNameRed(false);
        }
    },[lName])

    useEffect(()=>
    {
        if(changedUser && username=="")
        {
            setUserRed(true);
        }
        else
        {
            setUserRed(false);
        }
    },[username])

    useEffect(()=>
    {
        if(changedPass && pass=="")
        {
            setPassRed(true);
        }
        else
        {
            setPassRed(false);
        }
    },[pass])    
    
    useEffect(()=>
    {
        if(changedPhone && phone=="")
        {
            setPhoneRed(true);
        }
        else
        {
            setPhoneRed(false);
        }
    },[phone])

    
    function changeFName(event: React.ChangeEvent<HTMLInputElement>)
    {
        setFName(event.target.value);
        setChangedFName(true);
    }

    function changeLName(event: React.ChangeEvent<HTMLInputElement>)
    {
        setLName(event.target.value);
        setChangedLName(true);
    }

    function changeUser(event: React.ChangeEvent<HTMLInputElement>)
    {
        setUser(event.target.value);
        setChangedUser(true);
    }

    function changePass(event: React.ChangeEvent<HTMLInputElement>)
    {
        setPass(event.target.value);
        setChangedPass(true);
    }

    function changePhone(event: React.ChangeEvent<HTMLInputElement>)
    {
        console.log(phone); 
        setPhone(event.target.value);
        setChangedPhone(true);
    }

    function submit()
    {
        let data = JSON.stringify({
          "firstName": fName,
          "lastName": lName,
          "email": username,
          "password": pass,
          "phoneNumber": phone
        });
        
        let config = {
          method: 'post',
          maxBodyLength: Infinity,
          url: 'http://localhost:8080/api/auth/register',
          headers: { 
            'Content-Type': 'application/json'
          },
          data : data
        };
        
        axios.request(config)
        .then((response) => {
          console.log(JSON.stringify(response.data));
        })
        .catch((error) => {
          console.log(error);
        });
        
        
    }

    return (
        <div>
            <div id="Register-popup"
                className="bg-black/75 registerContainer">
                <div className="relative p-4 w-full max-w-md h-full md:h-auto">

                    <div className="relative bg-zinc-900 rounded-lg shadow">
                        <button type="button" 
                            className="exitButton absolute top-3 right-2.5 text-gray-400 bg-transparent rounded-lg text-sm p-1.5 ml-auto inline-flex items-center popup-close"><svg
                                aria-hidden="true" className="w-5 h-5" fill="#c6c7c7" viewBox="0 0 20 20"
                                xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd"
                                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                    ></path>
                            </svg>
                            <span className="sr-only">Close popup</span>
                        </button>

                        <div className="p-5">
                            <p className="mb-4 text-sm font-normal text-gray-800"></p>

                            <div className="text-center">
                                <p className="mb-6 text-2xl font-semibold leading-5 purpleText">
                                    Register
                                </p>
                            </div>


                            <form className="w-full flex flex-wrap gap-0 justify-center">
                                
                                <div className="w-1/2">
                                    <h1 className="text-white font-light mt-2">First Name</h1>
                                    <input name="firstName" type="text" 
                                        className={fNameRed?
                                            "inputName text-gray-400 mt-1 block w-[96%] rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            :"inputName text-gray-400 mt-1 block w-[96%] rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={changeFName}
                                        value={fName} />
                                    {fNameRed?<p className="font-normal text-red-500 text-xs pt-2">First Name cannot be empty</p>:<></>}
                                </div>

                                <div className="w-1/2">
                                    <h1 className="text-white font-light mt-2">Last Name</h1>
                                    <input name="lastName" type="text" 
                                        className={lNameRed?
                                            "inputName text-gray-400 mt-1 block w-[100%] rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            :"inputName text-gray-400 mt-1 block w-[100%] rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={changeLName}
                                        value={lName} />
                                    {lNameRed?<p className="font-normal text-red-500 text-xs pt-2">Last Name cannot be empty</p>:<></>}
                                </div>

                                <div className="w-full">                                
                                    <h1 className="text-white font-light mt-2">Email</h1>
                                    <input name="email" type="email"
                                        className={userRed?
                                            "inputUser text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            :"inputUser text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={changeUser}
                                        value={username} />
                                    {userRed?<p className="font-normal text-red-500 text-xs pt-2">Email cannot be empty</p>:<></>}
                                </div>

                                <div className="w-full">                                
                                    <h1 className="text-white font-light mt-2">Password</h1>
                                    <input name="password" type="password" 
                                        className={passRed?
                                            "inputPass text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            :"inputPass text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={changePass}
                                        value={pass} />
                                    {passRed?<p className="font-normal text-red-500 text-xs pt-2">Password cannot be empty</p>:<></>}
                                </div>

                                <div className="w-full">
                                    <h1 className="text-white font-light mt-2">Phone Number</h1>
                                    <input name="Phone Number" type="text" 
                                        className={phoneRed?
                                            "inputPhone text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-red-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                                            :"inputPhone text-gray-400 mt-1 block w-full rounded-lg border bg-zinc-900 border-gray-700 px-3 py-2 pl-12 shadow-sm outline-none placeholder:text-gray-400 focus:border-gray-500 focus:ring-1 focus:ring-gray-500"}
                                        autoComplete="off"
                                        onChange={changePhone}
                                        value={phone} />
                                    {phoneRed?<p className="font-normal text-red-500 text-xs pt-2">Phone Number cannot be empty</p>:<></>}
                                </div>
                            </form>
                            
                            <div className="submitButtonContainer mt-5">
                                    <AnimatedButton 
                                        onClick={submit}
                                    >
                                        <div className="submitButton purpleText">Submit</div>
                                    </AnimatedButton>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Register;