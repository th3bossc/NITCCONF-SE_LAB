import { Tag } from "@/types"

const Tags = ({
    id,
    title,
} : Tag) => {
    return (
        <span className="px-3 py-1 bg-nitconfprimary text-white rounded-lg"> 
            {title} 
        </span>
    )
}

export default Tags;