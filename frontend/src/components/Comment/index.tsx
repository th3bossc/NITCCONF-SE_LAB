import { Review } from "@/types";

const Comment = ({
    comment,
    reviewer,
}: Review) => {
    return (
        <div className="w-full bg-[#ddd] rounded-lg p-2 ps-4 pe-4 flex flex-col gap-2">
            <div className="w-full flex justify-between items-center font-regular text-lg xl:text-xl">
                <span className="max-w-[50%] overflow-hidden text-ellipsis"> {reviewer.firstName} {reviewer.lastName} </span>
                <span className="max-w-[50%] overflow-hidden text-ellipsis"> {reviewer.email} </span>
            </div>
            <hr className="pt-[0.1rem] pb-[0.1rem] bg-white text-white w-full" />
            <div className="w-full font-medium text-lg xl:text-xl">
                {comment}
            </div>
        </div>
    )
}

export default Comment;