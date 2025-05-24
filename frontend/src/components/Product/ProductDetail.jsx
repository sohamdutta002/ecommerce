import React, { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useGetReq } from '../../hooks/useHttp';
import { AuthContext } from '../../context/AuthContext';

const ProductDetail = () => {

    const { productId } = useParams();
    const { token } = useContext(AuthContext);
    const [product, setProduct] = useState();
    const [loading, setLoading] = useState();
    const fetchProductDetails = async () => {
        try {
            // console.log(productId)
            const data = await useGetReq(`api/products/${productId}`, null, token);
            setProduct(data);
            // console.log(data);
            return data;
        } catch (e) {
            throw new Error(e);
        } finally {
            setLoading(false);
        }
    }
    useEffect(() => {
        fetchProductDetails();
    }, [productId])
    return (
        <div>
            {loading ? <p className='text-center text-lg text-purple-600'>Loading...</p> :
                <div className='min-h-screen'>
                    {
                        product ?
                            <div className=''>
                                <p className='text-gray-500 uppercase'>{product.category}</p>
                                <div className="flex p-4 md:p-12 gap-4 justify-around items-center">
                                    <div className="w-1/2 h-[600px]">
                                        <img src={product.imageUrl} alt={product.name} className='h-full object-cover' />
                                    </div>
                                    <div className="w-1/2 flex flex-col">
                                        <p className='text-3xl md:text-5xl font-bold text-purple-800 mt-4'>{product.name}</p>
                                        <p className='text-lg md:text-xl text-gray-600 mt-4'>{product.description}</p>
                                        <p className='text-2xl md:text-4xl font-bold text-purple-700 mt-6'>&#8377; {product.price}</p>
                                        <button className='bg-purple-600 rounded-lg px-2 py-3 text-white text-lg mt-4 w-8/12 hover:bg-purple-400 hover:cursor-pointer'>Add to Cart</button>
                                        <button className='bg-purple-600 rounded-lg px-2 py-3 text-white text-lg mt-4 w-8/12 hover:bg-purple-400 hover:cursor-pointer'>Buy Now</button>
                                    </div>
                                </div>
                            </div> :
                            <p className='text-center text-lg text-gray-500'>Product not available</p>
                    }
                </div>}
        </div>
    )
}

export default ProductDetail
