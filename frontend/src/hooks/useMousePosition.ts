import { useState, useEffect, RefObject } from "react";



const useMousePosition = ({ ref }: { ref?: RefObject<HTMLDivElement> }) => {
    const [mousePosition, setMousePosition] = useState<{ x: number, y: number }>({ x: 0, y: 0 });

    useEffect(() => {
        const updateMousePosition = (e: MouseEvent) => {
            if (ref?.current) {
                const { left, top } = ref?.current.getBoundingClientRect();
                setMousePosition({ x: e.clientX - left, y: e.clientY - top });
            }

        };

        window.addEventListener("mousemove", updateMousePosition);
        return () => window.removeEventListener("mousemove", updateMousePosition);

    }, [ref]);

    return mousePosition;

};

export default useMousePosition;