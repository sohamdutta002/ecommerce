import React, { useContext, useEffect, useState } from 'react'
import { useGetReq } from '../../hooks/useHttp'
import { AuthContext } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const ProductList = () => {

    const navigate=useNavigate();
    const { token } = useContext(AuthContext);
    const [loading, setLoading] = useState(true);
    const [products, setProducts] = useState();
    const fetchAllProducts = async () => {
        try {
            const data = await useGetReq('api/products', null, token);
            // console.log(data);
            setProducts(data);
            return data;
        } catch (e) {
            throw new Error(e.message);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchAllProducts();
    }, [])

    const handleClick = (productId) => {
        navigate(`/${productId}`);
    }

    const handleAddCart = () => {

    }


    return (
        <div className='p-4 md:p-8 bg-gradient-to-b from-purple-50 to-purple-100 min-h-screen'>
            <h1 className='text-2xl md:text-4xl font-bold text-center mb-8'>Our Products</h1>
            {
                loading ? (<p className='text-center text-lg'>Loading...</p>) :
                    products?.length > 0 ?
                        <div className='flex flex-wrap justify-between gap-6'>
                            {products.map(product =>
                                <div key={product.productId} className='bg-white w-[400px] rounded-lg overflow-hidden shadow-md shadow-gray-400 hover:shadow-xl transition-shadow duration-300'>
                                    <div className="h-[400px] overflow-hidden" onClick={()=>handleClick(product.productId)}>
                                        <img src={product.imageUrl} alt={product.name} className='object-contain cursor-pointer' />
                                    </div>
                                    <div className="p-4 flex flex-col justify-between flex-grow">
                                        <h2 className='text-xl font-semibold text-purple-800 cursor-pointer' onClick={()=>handleClick(product.productId)}>{product.name?.length>30?product.name.slice(0,30)+'...':product.name}</h2>
                                        <p className='text-lg font-bold text-purple-600 mt-4 cursor-pointer' onClick={()=>handleClick(product.productId)}>&#8377; {product.price}</p>
                                        <button className='bg-purple-600 text-white py-2 px-4 rounded-lg hover:bg-purple-500 transition-colors duration-300 cursor-pointer' onClick={handleAddCart}>Add to Cart</button>
                                    </div>
                                </div>
                            )
                            }
                        </div> : <p className='text-center text-lg text-gray-500'>No Products</p>

            }
        </div>
    )
}

export default ProductList
