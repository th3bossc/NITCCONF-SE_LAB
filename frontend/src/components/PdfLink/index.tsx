import Image from "next/image";
import pdfImage from '/public/pdf.svg';
import Link from "next/link";
import { motion } from 'framer-motion';
import { Status } from "@/types";
import { useEffect, useState } from "react";
const PdfLink = ({
    id,
    status,
} : {
    id: string,
    status: Status
}) => {
    const [bgColor, setBgColor] = useState<string>("");
    useEffect(() => {
        if (status == "PENDING")
            setBgColor("#d4d4d4")
        else if (status == "ACCEPTED")
            setBgColor("#00AB41")
        else    
            setBgColor("#B13C39")
    }, [status])
    return (
    <div className="xl:absolute top-10 2xl:top-16 right-10 2xl:right-16 flex xl:flex-col justify-center xl:justify-start items-center xl:items-start gap-4 mt-4 xl:mt-0">
        <motion.div 
            whileTap={{scale: 0.95}}
            whileHover={{rotate: 5}}
            className="w-[40px] h-[40px] xl:w-[200px] xl:h-[200px] bg-black rounded-lg flex justify-center items-center"
        >
            <Link href={`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/session/doc/${id}`}>
                    <Image src={pdfImage} alt="pdf" width={100} height={100} />
            </Link>
        </motion.div>
        <motion.span 
            className="w-[200px] text-center rounded-lg font-medium p-2"
            style={{
                backgroundColor: bgColor,
                color: status === 'REJECTED' ? "#fff" : "#000",
            }}
        > 
            {status} 
        </motion.span>
    </div>
    )
}

export default PdfLink;