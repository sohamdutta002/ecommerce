const BASE_URL = "http://localhost:8080";


export const usePostReq=async (url,body,token)=>{
    try{
        // console.log(body);
        const response=await fetch(`${BASE_URL}/${url}`,{
            method: "POST",
            body: JSON.stringify(body),
            headers:{
                "Content-Type":"application/json",
                Authorization: token?`BEARER ${token}`:"",
            },
        });

        const data=await response.json();
        
        if(!response.ok){
            throw new Error(response);
        }
        // console.log(data);
        return data;
    }   catch(e){
        console.log("Post Request Error:",e.message);
        throw e;
    }
}

export const useGetReq=async (url,body,token)=>{
    try{
        const response=await fetch(`${BASE_URL}/${url}`,{
            method: "GET",
            body:body,
            headers:{
                "Content-Type":"application/json",
                Authorization: `BEARER ${token}`,
            }
        });

        const data=await response.json();

        if(!response.ok){
            throw new Error(response);
        }

        return data;
    }   catch(e){
        console.log("Get Method Error:",e.message);
        throw e;
    }
}