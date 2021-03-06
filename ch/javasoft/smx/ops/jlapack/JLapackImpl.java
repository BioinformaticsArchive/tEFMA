/*
 * =============================================================================
 * Simplified BSD License, see http://www.opensource.org/licenses/
 * -----------------------------------------------------------------------------
 * Copyright (c) 2008-2009, Marco Terzer, Zurich, Switzerland
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, 
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Swiss Federal Institute of Technology Zurich 
 *       nor the names of its contributors may be used to endorse or promote 
 *       products derived from this software without specific prior written 
 *       permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * =============================================================================
 */
package ch.javasoft.smx.ops.jlapack;

import org.netlib.util.intW;

import ch.javasoft.smx.iface.DoubleMatrix;
import ch.javasoft.smx.iface.ReadableDoubleMatrix;
import ch.javasoft.smx.ops.ext.ExternalOpsImpl;
import ch.javasoft.util.numeric.Zero;

public class JLapackImpl extends ExternalOpsImpl{

	private final Zero mZero;
    
	public JLapackImpl() {
        this(new Zero());
    }
    
    public JLapackImpl(Zero zero) {    	
    	mZero = zero;
    }
	@Override
	public DoubleMatrix invert(ReadableDoubleMatrix src) {
		// TODO implement
		throw new RuntimeException("not implemented");
	}

	@Override
	public int nullity(ReadableDoubleMatrix src) {
		// TODO implement
		throw new RuntimeException("not implemented");
	}

	@Override
	public DoubleMatrix nullspace(ReadableDoubleMatrix src) {
		// TODO implement
		throw new RuntimeException("not implemented");
	}

	public int rank(double[][] matrix) {
		double[] svals = singularValues(matrix);
		return rank(svals);		
	}
	@Override
	public int rank(ReadableDoubleMatrix src) {
		return rank(src.getDoubleRows());
	}
	/**
    SUBROUTINE DGESDD( JOBZ, M, N, A, LDA, S, U, LDU, VT, LDVT, WORK,
    	     $                   LWORK, IWORK, INFO )
    	*
    	*  -- LAPACK driver routine (version 3.0) --
    	*     Univ. of Tennessee, Univ. of California Berkeley, NAG Ltd.,
    	*     Courant Institute, Argonne National Lab, and Rice University
    	*     October 31, 1999
    	*
    	*     .. Scalar Arguments ..
    	      CHARACTER          JOBZ
    	      INTEGER            INFO, LDA, LDU, LDVT, LWORK, M, N
    	*     ..
    	*     .. Array Arguments ..
    	      INTEGER            IWORK( * )
    	      DOUBLE PRECISION   A( LDA, * ), S( * ), U( LDU, * ),
    	     $                   VT( LDVT, * ), WORK( * )
    	*     ..
    	*
    	*  Purpose
    	*  =======
    	*
    	*  DGESDD computes the singular value decomposition (SVD) of a real
    	*  M-by-N matrix A, optionally computing the left and right singular
    	*  vectors.  If singular vectors are desired, it uses a
    	*  divide-and-conquer algorithm.
    	*
    	*  The SVD is written
    	*
    	*       A = U * SIGMA * transpose(V)
    	*
    	*  where SIGMA is an M-by-N matrix which is zero except for its
    	*  min(m,n) diagonal elements, U is an M-by-M orthogonal matrix, and
    	*  V is an N-by-N orthogonal matrix.  The diagonal elements of SIGMA
    	*  are the singular values of A; they are real and non-negative, and
    	*  are returned in descending order.  The first min(m,n) columns of
    	*  U and V are the left and right singular vectors of A.
    	*
    	*  Note that the routine returns VT = V**T, not V.
    	*
    	*  The divide and conquer algorithm makes very mild assumptions about
    	*  floating point arithmetic. It will work on machines with a guard
    	*  digit in add/subtract, or on those binary machines without guard
    	*  digits which subtract like the Cray X-MP, Cray Y-MP, Cray C-90, or
    	*  Cray-2. It could conceivably fail on hexadecimal or decimal machines
    	*  without guard digits, but we know of none.
    	*
    	*  Arguments
    	*  =========
    	*
    	*  JOBZ    (input) CHARACTER*1
    	*          Specifies options for computing all or part of the matrix U:
    	*          = 'A':  all M columns of U and all N rows of V**T are
    	*                  returned in the arrays U and VT;
    	*          = 'S':  the first min(M,N) columns of U and the first
    	*                  min(M,N) rows of V**T are returned in the arrays U
    	*                  and VT;
    	*          = 'O':  If M >= N, the first N columns of U are overwritten
    	*                  on the array A and all rows of V**T are returned in
    	*                  the array VT;
    	*                  otherwise, all columns of U are returned in the
    	*                  array U and the first M rows of V**T are overwritten
    	*                  in the array VT;
    	*          = 'N':  no columns of U or rows of V**T are computed.
    	*
    	*  M       (input) INTEGER
    	*          The number of rows of the input matrix A.  M >= 0.
    	*
    	*  N       (input) INTEGER
    	*          The number of columns of the input matrix A.  N >= 0.
    	*
    	*  A       (input/output) DOUBLE PRECISION array, dimension (LDA,N)
    	*          On entry, the M-by-N matrix A.
    	*          On exit,
    	*          if JOBZ = 'O',  A is overwritten with the first N columns
    	*                          of U (the left singular vectors, stored
    	*                          columnwise) if M >= N;
    	*                          A is overwritten with the first M rows
    	*                          of V**T (the right singular vectors, stored
    	*                          rowwise) otherwise.
    	*          if JOBZ .ne. 'O', the contents of A are destroyed.
    	*
    	*  LDA     (input) INTEGER
    	*          The leading dimension of the array A.  LDA >= max(1,M).
    	*
    	*  S       (output) DOUBLE PRECISION array, dimension (min(M,N))
    	*          The singular values of A, sorted so that S(i) >= S(i+1).
    	*
    	*  U       (output) DOUBLE PRECISION array, dimension (LDU,UCOL)
    	*          UCOL = M if JOBZ = 'A' or JOBZ = 'O' and M < N;
    	*          UCOL = min(M,N) if JOBZ = 'S'.
    	*          If JOBZ = 'A' or JOBZ = 'O' and M < N, U contains the M-by-M
    	*          orthogonal matrix U;
    	*          if JOBZ = 'S', U contains the first min(M,N) columns of U
    	*          (the left singular vectors, stored columnwise);
    	*          if JOBZ = 'O' and M >= N, or JOBZ = 'N', U is not referenced.
    	*
    	*  LDU     (input) INTEGER
    	*          The leading dimension of the array U.  LDU >= 1; if
    	*          JOBZ = 'S' or 'A' or JOBZ = 'O' and M < N, LDU >= M.
    	*
    	*  VT      (output) DOUBLE PRECISION array, dimension (LDVT,N)
    	*          If JOBZ = 'A' or JOBZ = 'O' and M >= N, VT contains the
    	*          N-by-N orthogonal matrix V**T;
    	*          if JOBZ = 'S', VT contains the first min(M,N) rows of
    	*          V**T (the right singular vectors, stored rowwise);
    	*          if JOBZ = 'O' and M < N, or JOBZ = 'N', VT is not referenced.
    	*
    	*  LDVT    (input) INTEGER
    	*          The leading dimension of the array VT.  LDVT >= 1; if
    	*          JOBZ = 'A' or JOBZ = 'O' and M >= N, LDVT >= N;
    	*          if JOBZ = 'S', LDVT >= min(M,N).
    	*
    	*  WORK    (workspace/output) DOUBLE PRECISION array, dimension (LWORK)
    	*          On exit, if INFO = 0, WORK(1) returns the optimal LWORK;
    	*
    	*  LWORK   (input) INTEGER
    	*          The dimension of the array WORK. LWORK >= 1.
    	*          If JOBZ = 'N',
    	*            LWORK >= 3*min(M,N) + max(max(M,N),6*min(M,N)).
    	*          If JOBZ = 'O',
    	*            LWORK >= 3*min(M,N)*min(M,N) + 
    	*                     max(max(M,N),5*min(M,N)*min(M,N)+4*min(M,N)).
    	*          If JOBZ = 'S' or 'A'
    	*            LWORK >= 3*min(M,N)*min(M,N) +
    	*                     max(max(M,N),4*min(M,N)*min(M,N)+4*min(M,N)).
    	*          For good performance, LWORK should generally be larger.
    	*          If LWORK < 0 but other input arguments are legal, WORK(1)
    	*          returns the optimal LWORK.
    	*
    	*  IWORK   (workspace) INTEGER array, dimension (8*min(M,N))
    	*
    	*  INFO    (output) INTEGER
    	*          = 0:  successful exit.
    	*          < 0:  if INFO = -i, the i-th argument had an illegal value.
    	*          > 0:  DBDSDC did not converge, updating process failed.
    	*
    	*  Further Details
    	*  ===============
    	*
    	*  Based on contributions by
    	*     Ming Gu and Huan Ren, Computer Science Division, University of
    	*     California at Berkeley, USA
    	*
    	*  =====================================================================
    	*
    	*/
//	private static void DGESDD(String JOBZ, int M, int N, double[][] A, double[] LDA, double[][] S, double[][] U, 
//			double[] LDU, int VT, int[] LDVT, int WORK, intW LWORK /*, int IWORK, int INFO*/) {
//		
//		org.netlib.lapack.DGESDD.DGESDD(JOBZ, M, N, A, LDA, S, U, LDU, VT, LDVT, LWORK);  
//	}
//	
	private static double[] singularValues(double[][] mx) {
                // cj: b
		// int M = mx.length;
		// int N = M == 0 ? 0 : mx[0].length;
		// double[] svals = new double[Math.min(M, N)];
		// int lwork = 3*Math.min(M,N)*Math.min(M,N) +
                //             Math.max(Math.max(M,N),4*Math.min(M,N)*Math.min(M,N)+4*Math.min(M,N));
		// double[] work = new double[lwork];
		// int[] iwork = new int[8*Math.min(M,N)];
		// intW info = new intW(0);
		// org.netlib.lapack.DGESDD.DGESDD("N", M, N, mx, svals, EMPTY_DBL_MATRIX, EMPTY_DBL_MATRIX,
                // work, lwork, iwork, info);
                // cj: e


		
                // cj: b
                java.lang.String jobz = "N";
                double[] a = new double[mx.length*mx[0].length];

                int index = 0;
                for( int i = 0; i < mx[0].length; i++ )
                {
                   for( int j = 0; j < mx.length; j++ )
                   {
                      a[index++] = mx[j][i];
                   }
                }
                int M = mx.length;
                int N = M == 0 ? 0 : mx[0].length;
                int lda = Math.max(1,M);
                int ldu = 1; // dummy value, as u is not referenced because of Ejobz.eq."N"
                int ldvt = 1; // dummy value, as vt is not referenced because of Ejobz.eq."N"
                int lwork = 3*Math.min(M,N)*Math.min(M,N) + Math.max(Math.max(M,N),7*Math.min(M,N));
                double[] s = new double[Math.min(M,N)];
                double[] u = new double[1]; // dummy array, which is not referenced, as jobz.eq."N"
                double[] vt = new double[1]; // dummy array, which is not referenced, as jobz.eq."N"
                double[] work = new double[lwork];
                int[] iwork = new int[8*Math.min(M,N)];
                intW info = new intW(0);

                org.netlib.lapack.Dgesdd.dgesdd("N", M, N, a, 0, lda, s, 0, u, 0, ldu, vt, 0,
                                                ldvt, work, 0, lwork, iwork, 0, info);
                // cj: e
		
		if (info.val == 0) {
			// return svals;
			return s;
		}
		else {
			throw new RuntimeException("lapack dgesdd failed with info code " + info.val);
		}		
		
	}
    private static final double[][] EMPTY_DBL_MATRIX = new double[1][1];
	private int rank(double[] sing) {
		int rank = sing.length;
		for (int ii = 0; ii < sing.length; ii++) {
			if (sing[ii] <= mZero.mZeroPos) {
				rank = ii;
				break;
			}
		}
		return rank;
	}

}
