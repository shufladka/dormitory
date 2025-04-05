import { ofetch } from "ofetch";
import type { FetchOptions, ResponseType } from "ofetch";

export interface OurFetchOptions<T extends ResponseType>
  extends FetchOptions<T> {
  withToken?: boolean;
}

export function $fetch<T = any, R extends ResponseType = "json">(
    url: string,
    options: OurFetchOptions<R> = {}
  ) {
    const baseBackendURL = "http://localhost:8180";
  
    const { baseURL = baseBackendURL, ...opts } = options;
  
    return ofetch<T, R>(url, {
      ...opts,
      baseURL,
    });
  }