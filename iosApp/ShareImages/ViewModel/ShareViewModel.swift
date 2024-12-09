//
//  ShareViewModel.swift
//  iosApp
//
//  Created by Miguel Ferrer on 5/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import UniformTypeIdentifiers

// MARK: - ShareViewModel
final class ShareViewModel: ObservableObject {
    private let info: ShareViewInfo
    
    @Published var image = UIImage()
    
    init(with info: ShareViewInfo) {
        self.info = info
    }
}

// MARK: - Logic
extension ShareViewModel {
    func loadImage() {
        info.itemProvider.loadDataRepresentation(forTypeIdentifier: UTType.image.identifier) { data, error in
            guard let data, let image = UIImage(data: data) else { return }
            let aspectRatio = image.size.height / image.size.width
            let width = UIScreen.main.bounds.width - 40
            let height = width * aspectRatio
            let size = CGSize(width: width, height: height)
            guard let preview = image.preparingThumbnail(of: size) else { return }
            self.image = preview
        }
    }
    
    func getMoreInfo() {
        // TODO: Get More Info
    }
    
    func dismiss() {
        info.extensionContext.completeRequest(returningItems: [])
    }
}
