"use client";

import PaperPage from "@/components/PaperPage";
import { useAuthContext } from "@/hooks/useAuthContext";
import { Paper } from "@/types";
import { useEffect, useState } from "react";

const SlugPage = ({ params }: { params: { id: string } }) => {
    const [paper, setPaper] = useState<Paper | null>(null);
    const { papers } = useAuthContext();
    useEffect(() => {
        setPaper(papers.find((paper) => paper.id === params.id) || null);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    return (
        <div>
            <PaperPage paper={paper} />
        </div>
    );
}

export default SlugPage;