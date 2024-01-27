import { Review } from "@/types";

const Comment = ({
    id,
    comment,
    reviewer,
}: Review) => {
    return (
        <div className="w-full bg-[#ddd] rounded-lg p-2 ps-4 pe-4 flex flex-col gap-2">
            <div className="w-full flex justify-between font-regular text-lg xl:text-xl">
                <span> {reviewer.firstName} {reviewer.lastName} </span>
                <span> {reviewer.email} </span>
            </div>
            <hr className="pt-[0.1rem] pb-[0.1rem] bg-white text-white w-full" />
            <div className="w-full font-medium text-lg xl:text-xl">
                {comment}
            </div>
        </div>
    )
}

export default Comment;