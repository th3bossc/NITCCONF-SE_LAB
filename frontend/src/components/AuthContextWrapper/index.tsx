"use client";

const AuthContextwrapper = ({
    children
}: {
    children : React.ReactNode,
}) => {
    return (
        <AuthContextwrapper>
            {children}
        </AuthContextwrapper>
    )
}

export default AuthContextwrapper;