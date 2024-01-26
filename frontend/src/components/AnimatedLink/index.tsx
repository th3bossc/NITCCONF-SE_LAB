"use client";

import { useState } from "react";
import TextAnimation from "../TextAnimation";



const AnimatedLink = ({
    title,
    href,
}: {
    title: string,
    href: string,
}) => {
    const [hovered, setHovered] = useState(false);
    return (
        <div
            className="relative cursor-pointer overflow-hidden z-10 text-nitconfprimary"
            onMouseEnter={() => setHovered(true)}
            onMouseLeave={() => setHovered(false)}
        >
            <TextAnimation title={title} href={href} top={true} hovered={hovered} />
            <div className={`absolute top-0`}>
                <TextAnimation title={title} href={href} top={false} hovered={hovered} />
            </div>
        </div>
    )
}

export default AnimatedLink;