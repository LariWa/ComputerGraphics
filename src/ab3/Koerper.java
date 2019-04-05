package ab3;

import java.util.ArrayList;

public class Koerper {

	public Koerper() {
	}

	public float[][] getWuerfel(float[] A, float[] B, float[] C, float[] D, float[] E, float[] F, float[] G,
			float[] H) {
		ArrayList<Float> wuerfel = new ArrayList<>();
		ArrayList<Float> color = new ArrayList<>();
		ArrayList<Float> normalen = new ArrayList<>();
		ArrayList<Float> uv = new ArrayList<>();

		setSquare(wuerfel, color, normalen, uv, A, B, C, D);
		setSquare(wuerfel, color, normalen, uv, E, H, G, F);
		setSquare(wuerfel, color, normalen, uv, A, E, F, B);
		setSquare(wuerfel, color, normalen, uv, H, D, C, G);
		setSquare(wuerfel, color, normalen, uv, C, B, F, G);
		setSquare(wuerfel, color, normalen, uv, D, H, E, A);

		float[] Wuerfel = new float[wuerfel.size()];
		float[] Color = new float[color.size()];
		float[] Normalen = new float[normalen.size()];
		float[] UV = new float[uv.size()];
		for (int i = 0; i < wuerfel.size(); i++) {
			Wuerfel[i] = wuerfel.get(i);
			Color[i] = color.get(i);

			Normalen[i] = normalen.get(i);
			if (i < uv.size())
				UV[i] = uv.get(i);
		}

		float[][] result = { Wuerfel, Color, Normalen, UV };
		return result;
	}

	public void setPoint(ArrayList<Float> wuerfel, ArrayList<Float> color, float[] Punkt) {
		for (int i = 0; i < 3; i++) {
			wuerfel.add(Punkt[i]);
		}
		for (int i = 3; i < 6; i++) {
			color.add(Punkt[i]);
		}
		//		for (int i = 6; i < 8; i++) {
		//			uv.add(Punkt[i]);
		//		}

	}

	public float[][] getDodekaeder(float[] A, float[] B, float[] C, float[] D, float[] E, float[] F, float[] G,
			float[] H, float[] K, float[] L, float[] M, float[] N, float[] O, float[] P, float[] Q, float[] R,
			float[] S, float[] T, float[] U, float[] V) {
		ArrayList<Float> dodekaeder = new ArrayList<>();
		ArrayList<Float> color = new ArrayList<>();
		ArrayList<Float> normalen = new ArrayList<>();

		setFünfeck(dodekaeder, color, normalen, A, P, O, E, K);
		setFünfeck(dodekaeder, color, normalen, A, K, L, B, M);
		setFünfeck(dodekaeder, color, normalen, A, M, N, D, P);
		setFünfeck(dodekaeder, color, normalen, Q, C, N, M, B);
		setFünfeck(dodekaeder, color, normalen, Q, B, L, F, R);
		setFünfeck(dodekaeder, color, normalen, Q, R, G, S, C);
		setFünfeck(dodekaeder, color, normalen, S, T, D, N, C);
		// setFünfeck(dodekaeder, color, normalen, S, C, Q, R, G);
		setFünfeck(dodekaeder, color, normalen, S, G, V, H, T);
		setFünfeck(dodekaeder, color, normalen, U, F, L, K, E);
		setFünfeck(dodekaeder, color, normalen, U, V, G, R, F);
		setFünfeck(dodekaeder, color, normalen, U, E, O, H, V);
		setFünfeck(dodekaeder, color, normalen, H, O, P, D, T);

		float[] Dodekaeder = new float[dodekaeder.size()];
		float[] Color = new float[color.size()];
		float[] Normalen = new float[normalen.size()];
		for (int i = 0; i < dodekaeder.size(); i++) {
			Dodekaeder[i] = dodekaeder.get(i);
			Color[i] = color.get(i);
			Normalen[i] = normalen.get(i);
		}
		float[][] result = { Dodekaeder, Color, Normalen };
		return result;
	}

	public void setFünfeck(ArrayList<Float> dodekaeder, ArrayList<Float> color, ArrayList<Float> normalen, float[] A,
			float[] B, float[] C, float[] D, float[] E) {
		setPoint(dodekaeder, color, A);
		setPoint(dodekaeder, color, D);
		setPoint(dodekaeder, color, E);

		setPoint(dodekaeder, color, A);
		setPoint(dodekaeder, color, B);
		setPoint(dodekaeder, color, D);

		setPoint(dodekaeder, color, B);
		setPoint(dodekaeder, color, C);
		setPoint(dodekaeder, color, D);

		setNormalenVektor(A, E, D, normalen);
		setNormalenVektor(A, E, D, normalen);
		setNormalenVektor(A, E, D, normalen);
	}

	public void setSquare(ArrayList<Float> wuerfel, ArrayList<Float> color, ArrayList<Float> normalen,
			ArrayList<Float> uv, float[] A, float[] B, float[] C, float[] D) {
		setPoint(wuerfel, color, A);
		setPoint(wuerfel, color, B);
		setPoint(wuerfel, color, C);
		setPoint(wuerfel, color, A);
		setPoint(wuerfel, color, C);
		setPoint(wuerfel, color, D);

		setNormalenVektor(A, B, C, normalen);
		setNormalenVektor(A, B, C, normalen);
		setUV(uv);

	}

	public void setUV(ArrayList<Float> uv) {
		uv.add(1f);
		uv.add(0f);
		uv.add(1f);
		uv.add(1f);
		uv.add(0f);
		uv.add(1f);
		uv.add(1f);
		uv.add(0f);
		uv.add(0f);
		uv.add(1f);
		uv.add(0f);
		uv.add(0f);
	}

	public void setNormalenVektor(float[] A, float[] B, float[] C, ArrayList<Float> normalen) {
		float[] AB = new float[] { B[0] - A[0], B[1] - A[1], B[2] - A[2] };
		float[] AC = new float[] { C[0] - A[0], C[1] - A[1], C[2] - A[2] };
		float[] n = new float[] { AB[1] * AC[2] - AB[2] * AC[1], AB[2] * AC[0] - AB[0] * AC[2],
				AB[0] * AC[1] - AB[1] * AC[0] };
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				normalen.add(n[i]);
			}
		}
	}
}
